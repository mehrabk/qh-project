# محصول‌ساز یکپارچه قرض‌الحسنه — Modular Monolith (SOA-Style)

پیاده‌سازی Spring Boot برای مدل داده «محصول‌ساز یکپارچه سپرده و تسهیلات قرض‌الحسنه» (۴۷ جدول در ۵ پکیج محصول‌ساز)
به‌همراه ماژول Party مستقل (۱۸ جدول، معماری BIAN-style) — مجموعاً ۶ ماژول کسب‌وکاری.
معماری: **Modular Monolith** — هر قابلیت کسب‌وکاری در یک ماژول Maven جدا با پکیج جاوای اختصاصی
خودش زندگی می‌کند (Entity/Repository/Service/Controller مستقل)، اما همه با هم در یک اپلیکیشن
Spring Boot واحد (`qh-app`) اجرا می‌شوند. این یعنی می‌توانید هر ماژول را در آینده به یک
میکروسرویس مستقل تبدیل کنید بدون این‌که لازم باشد کد را از نو بازنویسی کنید.

## پشته فناوری

| مؤلفه | نسخه |
|---|---|
| Java | 21 (LTS) |
| Spring Boot | 4.1.1 (بر پایه Spring Framework 7، Jakarta EE) |
| Spring Data JPA / Hibernate | همراه Spring Boot 4.1.1 |
| پایگاه داده | H2 (in-memory، برای توسعه/دمو) |
| Build | Maven Multi-Module |
| Lombok | 1.18.36 |

## ساختار ماژول‌ها

```
qh-productbuilder (pom والد)
│
├── qh-common                 مشترک: BaseEntity، Auditing، Exception Handler، ApiResponse
│
├── qh-module-reference        05-Reference Data (۱۴ جدول) — بدون وابستگی به ماژول دیگر
│     ir.bank.qh.reference     DocumentType, LoanType, PlanType, LoanUsage,
│                              EconomicSection/SubSection, Operation/SubOperation,
│                              PatternOperation*، LoanProductCollateral، ...
│
├── qh-module-core             01-Core (۵ جدول) — وابسته به qh-common
│     ir.bank.qh.core          Product, ProductVersion, ProductVersionModule,
│                              ProductRelationship, ProductLegacyMapping
│
├── qh-module-common-rules     02-Common Rules (۹ جدول) — وابسته به core + reference
│     ir.bank.qh.commonrules   ProductEligibilityRule, ProductChannelRule/Operation,
│                              ProductOrgScope, ProductRequiredDocument/Inquiry,
│                              ProductPricingRule/Component، ProductRateTier
│
├── qh-module-deposit           03-Deposit Module (۱۳ جدول) — وابسته به core
│     ir.bank.qh.deposit       DepositProductProfile, OpeningRule, TermRule/AllowedTerm,
│                              TransactionRule, WithdrawalMedia, JointRule, HoldRule,
│                              DormancyRule, ClosureRule (+Precheck/Settlement/Approval)
│
├── qh-module-loan               04-Loan Module (۶ جدول) — وابسته به core + common-rules + reference
│     ir.bank.qh.loan          LoanProductProfile, EligibilityExtension, FinancialExtension,
│                              RepaymentRule, ProcessRule, CollateralRule
│
├── qh-module-party             ماژول Party (۱۸ جدول) — فقط وابسته به qh-common (کاملاً مستقل/SOA)
│     ir.bank.qh.party         PartyEntity (والد JOINED) → PersonEntity/OrganizationEntity،
│                              Address, ContactPoint, Identifier, Name, Role, Group/GroupMember,
│                              SignatureSpecimen, Inquiry, Demographic, Country, City, Branch
│
└── qh-app                     اپلیکیشن قابل اجرا؛ همه ماژول‌ها را جمع می‌کند + application.yml + data*.sql
      ir.bank.qh.app
```

هر ماژول یک ساختار پکیج یکسان دارد:

```
ir.bank.qh.<module>
├── entity/       @Entity ها (JPA)
├── repository/   Spring Data JPA (JpaRepository)
├── service/      منطق کسب‌وکار
└── controller/   کنترلر REST
```

**نکته مهم درباره ماژول Party:** برخلاف ۵ ماژول محصول‌ساز (که Service از `AbstractCrudService` و Controller
از `AbstractCrudController` در qh-common ارث‌بری می‌کنند)، طبق درخواست شما **ماژول Party کاملاً مستقل و
بدون وراثت از این کلاس‌های عمومی** پیاده‌سازی شده — هر Service و هر Controller تمام متدهای CRUD خودش را
به‌صورت مستقیم و صریح دارد. این هم با هدف استقلال کامل ماژول Party (آماده جداشدن به یک سرویس مجزا در آینده)
هم‌خوانی دارد.

### پایه‌های مشترک Entity ها (qh-common)

یک خانواده کلاس پایه در `ir.bank.qh.common.entity` وجود دارد و همه ماژول‌ها (core, commonrules,
deposit, loan, reference, party) از همین سلسله‌مراتب استفاده می‌کنند:

| کلاس پایه | برای چه مواردی | چه ماژول‌هایی استفاده می‌کنند |
|---|---|---|
| `BaseEntity` | Audit (`CREATED_BY/ON`, `MODIFIED_BY/ON`) با `java.util.Date`، `@DynamicUpdate`، و `@Version` روی `Long version` — بدون تنانت | داده‌های مرجع/ثابت مشترک بین همه نهادها: reference (کل ماژول)، و `CountryEntity`/`CityEntity`/`AddressEntity` در party |
| `TenantAwareEntity extends BaseEntity` | همان Audit به‌علاوه فیلد `institutionId` با انوتیشن `@TenantId` (قابلیت Hibernate 6/7) که به‌صورت خودکار و شفاف هر Query را به تنانت جاری فیلتر می‌کند | موجودیت‌های وابسته به یک نهاد بانکی: core, commonrules, deposit, loan (کل محصول‌ساز) و بیشتر Entity های party (Party, Branch, ContactPoint, ...) |

هر Entity، فیلد `id` (کلید اصلی) را خودش با `@Id`/`@GeneratedValue` تعریف می‌کند؛ کلاس‌های پایه فقط
Audit/تنانت را اضافه می‌کنند. قاعده انتخاب پایه: اگر داده‌تان **ثابت/مرجع** و مشترک بین همه نهادهاست از
`BaseEntity` استفاده کنید؛ اگر داده‌تان **مختص یک نهاد بانکی** (Institution) است از `TenantAwareEntity`
ارث ببرید.

### Multi-Tenancy (نهاد بانکی/Institution)

- `ir.bank.qh.common.tenant.TenantContext`: نگه‌دارنده تنانت جاری (`ThreadLocal`)، پیش‌فرض = نهاد `1`.
- `ir.bank.qh.common.tenant.TenantIdentifierResolver`: به Hibernate می‌گوید تنانت جاری کدام است.
- `ir.bank.qh.common.tenant.TenantConfig`: این Resolver را به‌صورت یک Bean اسپرینگی به Hibernate متصل می‌کند.
- `ir.bank.qh.common.tenant.TenantFilter`: هدر HTTP اختیاری `X-Institution-Id` را می‌خواند و برای طول
  درخواست تنظیم می‌کند؛ اگر هدر نباشد، پیش‌فرض نهاد `1` (همان نهادی که در `data-party.sql` seed شده) است.

⚠️ **این یک Scaffold پایه‌ی کاربردی است، نه یک پیاده‌سازی امنیتی کامل.** در پروژه واقعی معمولاً تنانت باید
از Principal احرازهویت‌شده (JWT/Session) استخراج شود، نه از یک هدر خام قابل جعل. همچنین چون امکان `mvn
compile` واقعی در محیط من نبود، توصیه می‌کنم رفتار `@TenantId` + `MULTI_TENANT_IDENTIFIER_RESOLVER` را در
اولین اجرای لوکال با دقت تست کنید (لاگ SQL تولیدشده باید `WHERE INSTITUTION_ID = ?` را روی جداول هر
موجودیتی که از `TenantAwareEntity` ارث می‌برد — نه فقط Party، بلکه core/commonrules/deposit/loan هم — نشان
دهد).

### Schema جدا برای هر ماژول — و اسم هر Schema از کانفیگ

هر ماژول جداول خودش را در یک Schema اختصاصی نگه می‌دارد (`@Table(schema = "...")`):

| ماژول | Schema منطقی |
|---|---|
| qh-module-reference | `REFERENCE` |
| qh-module-core | `CORE` |
| qh-module-common-rules | `COMMONRULES` |
| qh-module-deposit | `DEPOSIT` |
| qh-module-loan | `LOAN` |
| qh-module-party | `PARTY` |

مقدار `schema` روی `@Table` یک **نام منطقی ثابت** است، نه اسم فیزیکی نهایی — و هرگز از کد حذف نمی‌شود
(چون Hibernate مقدار این انوتیشن را باید در زمان کامپایل به‌صورت constant بداند، نه از یک property).
اسم **فیزیکی** واقعی هر Schema از `qh.schemas.*` در `application.yml` خوانده می‌شود:

```yaml
qh:
  schemas:
    reference: REFERENCE
    core: CORE
    commonrules: COMMONRULES
    deposit: DEPOSIT
    loan: LOAN
    party: PARTY
```

این نگاشت را `ir.bank.qh.common.config.ConfigurableSchemaNamingStrategy` (یک `PhysicalNamingStrategy`
که Spring Boot به‌طور خودکار شناسایی و به Hibernate متصل می‌کند) در لحظه تولید DDL/SQL اعمال می‌کند؛
یعنی برای انتقال یک ماژول به Schema دیگر فقط کافی است مقدار مربوطه را در `application.yml` (یا یک
Profile دیگر مثل `application-oracle.yml`) عوض کنید — نیازی به تغییر هیچ Entity نیست.

**نکته مهم درباره مرز واقعی Bounded Context:** این پنج Schema (`REFERENCE`, `CORE`, `COMMONRULES`,
`DEPOSIT`, `LOAN`) صرفاً یک تقسیم‌بندی **سازمانی/نام‌گذاری** داخل یک دامنه واحد به نام «محصول‌ساز
(Product Builder)» هستند — دقیقاً همان ۵ پکیجی که مدل اصلی (۴۷ جدول) تعریف کرده. این پنج همیشه با هم روی
یک دیتابیس/سرور می‌مانند و هرگز به‌تنهایی از هم جدا نمی‌شوند؛ به همین دلیل Entity های این پنج ماژول آزادانه
از `@ManyToOne`/`@JoinColumn` واقعی به هم استفاده می‌کنند (حتی وقتی Schema هایشان فرق دارد) و Hibernate هم
FK Constraint واقعی بین‌شان می‌سازد. مرز واقعی و تنها مرزی که ممکن است به یک سرور/دیتابیس جدا برود
**Party** است (ماژول کاملاً مستقل/SOA، طبق طراحی همان اول هم جدا بوده) — و چون هیچ Entity ای در محصول‌ساز
به Party (یا برعکس) رفرنس نمی‌دهد، این مرز از ابتدا هم تمیز بوده. اگر روزی محصول‌ساز خودش به چند سرویس
مجزا شکسته شود (نه فقط از Party جدا شود)، آن‌وقت باید دوباره تصمیم گرفت کدام از این ۵۲ رابطه باید به id
ساده (`Long`) برگردد — همان کاری که پیش از این تصحیح، اشتباهاً روی همه ۲۸ رابطه بین این پنج ماژول انجام
شده بود.

⚠️ این نگاشت فقط روی چیزهایی اعمال می‌شود که از مسیر Hibernate رد می‌شوند (Entity Mapping، DDL). فایل‌های
seed خام (`data-product.sql`, `data-party.sql`) نام Schema را به‌صورت متنی دارند (مثلاً
`INSERT INTO CORE.product ...`) و از این نگاشت عبور نمی‌کنند؛ اگر Schema پیش‌فرض `CORE` را عوض کنید، باید
seed خام را هم دستی هماهنگ کنید — این محدودیت ذاتی SQL خام است، نه چیزی که این مکانیزم قرار است حل کند.

`hibernate.hbm2ddl.create_namespaces=true` در `application.yml` باعث می‌شود Hibernate این Schemaهای
فیزیکی را خودش بسازد. اگر جدولی از ماژول دیگر را از طریق SQL خام (نه JPA) کوئری می‌گیرید، حتماً نام Schema
فیزیکی فعلی را هم بنویسید، مثلاً `SELECT * FROM CORE.PRODUCT` یا `SELECT * FROM PARTY.PARTY`.

**نقشه راه چند سرور (Oracle):** یک Profile نمونه در `application-oracle.yml` آماده شده که همین
`qh.schemas.*` را روی نام‌گذاری Oracle نشان می‌دهد (فعلاً یک تک‌دیتابیس Oracle با چند Schema، هنوز بدون
Driver واقعی روی classpath — قبل از استفاده واقعی، `ojdbc11` را به `qh-app/pom.xml` اضافه کنید). جدا
کردن واقعی روی یک **سرور/دیتابیس مستقل** یک قدم بزرگ‌تر و جداگانه است و طبق مرز Bounded Context بالا،
امروز فقط بین «محصول‌ساز» (این پنج Schema، همیشه با هم) و «Party» معنا دارد: هرکدام به یک
`DataSource`/`EntityManagerFactory` اختصاصی نیاز دارند و باید Repository هر طرف به همان مسیر وصل شود
(Multi-DataSource Routing). این مکانیزم `qh.schemas.*` همان سکوی پرشی است که آن مرحله را بدون دست‌زدن به
Entity ها ممکن می‌کند، اما خودش هنوز آن جداسازی فیزیکی را انجام نمی‌دهد.

## اجرای پروژه

```bash
cd qh-productbuilder
mvn clean install       # کامپایل همه ماژول‌ها
mvn -pl qh-app spring-boot:run
# یا
java -jar qh-app/target/qh-app.jar
```

اپلیکیشن روی پورت `8080` بالا می‌آید:

- REST API: `http://localhost:8080/api/v1/...`
- H2 Console: `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:qh_productbuilder;DB_CLOSE_DELAY=-1;NON_KEYWORDS=USER`
  - Username: `sa` / Password: (خالی)
- Health check: `http://localhost:8080/actuator/health`

## نمونه مسیرهای REST

هر جدول یک کنترلر CRUD کامل دارد (`GET` لیست، `GET /{id}`، `POST`، `PUT /{id}`، `DELETE /{id}`)
زیر مسیر `/api/v1/product/{module}/{entity-kebab-plural}` برای ماژول‌های محصول‌ساز، و
`/api/v1/party/{entity-kebab-plural}` برای ماژول Party:

```
GET  /api/v1/product/core/products
GET  /api/v1/product/core/products/1001
GET  /api/v1/product/core/product-versions/1101
POST /api/v1/product/core/product-versions/1101/approve?approvedBy=COMMITTEE
POST /api/v1/product/core/product-versions/1101/enable-origination

GET  /api/v1/product/commonrules/product-eligibility-rules
GET  /api/v1/product/deposit/deposit-product-profiles
GET  /api/v1/product/loan/loan-product-profiles

GET  /api/v1/product/reference/loan-types
GET  /api/v1/product/reference/plan-types

GET  /api/v1/party/parties                 (فقط خواندنی؛ Person/Organization به‌صورت چندریختی)
GET  /api/v1/party/persons/1001
GET  /api/v1/party/organizations/2001
GET  /api/v1/party/party-roles
GET  /api/v1/party/party-groups
```

برای ماژول Party، هدر اختیاری `X-Institution-Id` مشخص می‌کند کدام نهاد بانکی؛ اگر نفرستید، پیش‌فرض `1`
(همان نهاد seed‌شده) استفاده می‌شود:

```
GET /api/v1/party/persons/1001
X-Institution-Id: 1
```

پاسخ‌ها در یک پوشش (envelope) یکسان برمی‌گردند:

```json
{
  "success": true,
  "data": { "...": "..." },
  "message": null,
  "timestamp": "2026-09-01T12:00:00Z"
}
```

## داده نمونه (H2)

هر بار اجرا، دو فایل seed پر می‌شوند (`spring.sql.init.data-locations`):

**`data-product.sql`** (همان دو سناریوی راهنمای محصول‌ساز):
- **PRODUCT_ID=1001 / PRODUCT_VERSION_ID=1101** → سپرده پس‌انداز قرض‌الحسنه (`DEP-QH-SAV-001`) کامل
  با Eligibility، Channel، Opening/Transaction/Withdrawal/Dormancy/Closure Rule
- **PRODUCT_ID=2001 / PRODUCT_VERSION_ID=2101** → تسهیلات قرض‌الحسنه (`LOAN-QH-001`) کامل با
  Profile، Eligibility Extension، Financial Extension، Repayment Rule، Process Rule، Collateral Rule
- کاتالوگ کامل داده مرجع (LoanType، PlanType، EconomicSection/SubSection، Collateral، Operation...)

**`data-party.sql`** (یک سناریوی کامل Party، نهاد بانکی = 1):
- **PARTY_ID=1001** → یک شخص حقیقی (`محمد رضایی`) با تاریخ تولد، جنسیت، ملیت، نام، کد ملی، تلفن همراه/ایمیل،
  آدرس محل سکونت، پروفایل جمعیت‌شناختی KYC، امضای نمونه، استعلام KYC انجام‌شده، و نقش CUSTOMER
- **PARTY_ID=2001** → یک شخص حقوقی (`شرکت تعاونی نمونه قرض‌الحسنه`) با شماره ثبت، تاریخ تأسیس، فرم حقوقی،
  آدرس دفتر مرکزی، تلفن ثابت، نقش CUSTOMER و استعلام لیست تحریم انجام‌شده
- رابطه‌ی نمایندگی: `محمد رضایی` با نقش `LEGAL_REPRESENTATIVE` (بر مبنای وکالت‌نامه) نماینده‌ی شرکت است
- گروه مالکیت (`PARTY_GROUP` نوع `OWNERSHIP`) با `محمد رضایی` به‌عنوان `OWNER` با ۱۰۰٪ مالکیت (ردیابی UBO)
- داده‌های مرجع پایه: ۲ کشور، ۳ شهر، ۲ شعبه

⚠️ همان‌طور که در مستند اصلی هم آمده: این مقادیر **صرفاً آموزشی/دمو** هستند، نه اطلاعات واقعی مشتری یا بانک.

## نمونه قواعد کسب‌وکار پیاده‌سازی‌شده (معادل CHECK Constraint های مدل اصلی)

| سرویس | قاعده |
|---|---|
| `ProductService` | `CK_PRODUCT_CLASS_NATURE`: محصول DEPOSIT باید LIABILITY، محصول LOAN باید ASSET باشد |
| `ProductVersionService` | `CK_PRODUCT_VERSION_DATES`: VALID_FROM نباید بعد از VALID_TO باشد؛ Origination فقط برای نسخه APPROVED فعال می‌شود |
| `ProductEligibilityRuleService` | `CK_PER_AGE_RANGE`: MIN_AGE نباید بزرگ‌تر از MAX_AGE باشد |

این الگو (override کردن `beforeCreate`/`beforeUpdate` در `AbstractCrudService`) را می‌توانید برای
باقی قواعد CHECK مدل اصلی هم تکرار کنید.

## چطور یک ماژول جدید اضافه کنم؟

دو الگو در پروژه وجود دارد؛ هرکدام را که برای ماژول جدیدتان مناسب‌تر است انتخاب کنید:

**الگوی A — ساده و سریع (مثل core/commonrules/deposit/loan/reference):** Service از
`AbstractCrudService` و Controller از `AbstractCrudController` (در qh-common) ارث می‌برند و فقط
دو متد override می‌شوند. برای CRUD ساده مناسب است.

**الگوی B — کاملاً مستقل (مثل party):** Service و Controller هیچ‌کدام از کلاس عمومی ارث نمی‌برند؛
تمام متدهای CRUD به‌صورت صریح در خود کلاس نوشته می‌شوند. برای ماژولی که قرار است بعداً به یک
سرویس مجزا (میکروسرویس) جدا شود یا منطق کسب‌وکار پیچیده‌تری دارد مناسب‌تر است.

فرض کنید می‌خواهید ماژول جدید «کارت بانکی» (`qh-module-card`) اضافه کنید:

1. یک پوشه Maven جدید بسازید: `qh-module-card/pom.xml` (کپی از یکی از ماژول‌های موجود، وابستگی
   به `qh-common` و هر ماژولی که نیاز دارید مثلاً `qh-module-core`).
2. پکیج `ir.bank.qh.card` را با زیرپکیج‌های `entity` / `repository` / `service` / `controller`
   بسازید، دقیقاً با همان الگوی ماژول‌های موجود.
3. Entity های خود را با `extends BaseEntity` **یا** `extends TenantAwareEntity` (هر دو در qh-common
   موجودند) بسازید؛ اگر داده‌تان مختص یک نهاد بانکی است از `TenantAwareEntity` استفاده کنید، وگرنه
   (داده مرجع/ثابت مشترک بین همه نهادها) از `BaseEntity`. برای Schema مستقل،
   `@Table(schema = "CARD", name = "...")` بگذارید.
4. Repository را `extends JpaRepository<YourEntity, Long>` بسازید.
5. Service را طبق الگوی A یا B بسازید؛ قواعد کسب‌وکار (معادل CHECK Constraint) را داخلش پیاده کنید.
6. Controller را طبق همان الگو بسازید و `@RequestMapping("/api/v1/card/your-entities")` بگذارید.
7. در `pom.xml` ریشه، `<module>qh-module-card</module>` را اضافه کنید.
8. در `qh-app/pom.xml`، `qh-module-card` را به عنوان `<dependency>` اضافه کنید.
9. اگر داده نمونه دارید، یک `data-card.sql` بسازید و آن را به لیست `spring.jpa.sql.init.data-locations`
   در `application.yml` اضافه کنید.

همین! نیازی به تغییر `QhProductBuilderApplication` نیست چون component-scan روی کل
`ir.bank.qh` است و ماژول جدید را خودکار پیدا می‌کند.

## نکات فنی مهم (طبق راهنمای اصلی)

- کلید خواندن Ruleها همیشه `PRODUCT_VERSION_ID` است، نه `PRODUCT_ID` (هر Rule Entity به
  `ProductVersion` وصل است، نه مستقیم به `Product`).
- `RECORD_VERSION` روی همه Entity ها به‌صورت `@Version` (Optimistic Locking) پیاده شده است.
- Auditing (`CREATED_AT/BY`, `UPDATED_AT/BY`) خودکار توسط Spring Data JPA Auditing انجام می‌شود
  (`JpaAuditingConfig` در `qh-common`؛ در دمو کاربر ثابت `SYSTEM` برگردانده می‌شود — در پروژه
  واقعی باید به Spring Security متصل شود).
- `spring.jpa.open-in-view=true` عمداً فعال است تا لود Lazy روابط هنگام serialize کردن JSON با
  خطا مواجه نشود؛ برای پروژه Production توصیه می‌شود این را با DTO/Projection جایگزین کنید.

## محدودیت شناخته‌شده

این پروژه در یک محیط sandbox بدون دسترسی به Maven Central تولید شده، بنابراین **کامپایل واقعی
با `mvn compile` تست نشده است.** کد به‌صورت دستی و دقیق بازبینی شده (تعادل آکولاد در تمام ۳۱۰ فایل
جاوا، صحت importها/پکیج‌ها پس از هر بازنویسی، تطابق نام ستون‌ها بین Entity و data.sql) اما توصیه
می‌شود اولین اجرا را با توجه ویژه به لاگ خطاهای کامپایل/Hibernate انجام دهید. رایج‌ترین خطاهای
احتمالی معمولاً نسخه‌ناسازگاری (مثلاً اگر Spring Boot 4.1.1 هنوز روی ریپازیتوری لوکال شما موجود
نباشد) هستند که با تغییر نسخه در `pom.xml` والد قابل رفع‌اند.

نکته‌ی اضافه درباره Multi-Tenancy: زیرساخت آن (`@TenantId` + `CurrentTenantIdentifierResolver`) بر اساس
مستندات رسمی Hibernate 6/7 نوشته شده و اکنون روی همه ماژول‌هایی که Entity‌شان از `TenantAwareEntity` ارث
می‌برد (party و همه ماژول‌های محصول‌ساز: core, commonrules, deposit, loan) اعمال می‌شود؛ اما چون امکان
اجرای واقعی نداشتم، **حتماً در اولین اجرا بررسی کنید** که کوئری‌های تولیدشده روی این جداول واقعاً
`WHERE INSTITUTION_ID = ?` را اعمال می‌کنند (با `show-sql: true` در لاگ قابل مشاهده است).
