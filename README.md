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
│     ir.bank.qh.productbuilder.reference     DocumentType, LoanType, PlanType, LoanUsage,
│                                              EconomicSection/SubSection, Operation/SubOperation,
│                                              PatternOperation*، LoanProductCollateral، ...
│
├── qh-module-core             01-Core (۵ جدول) — وابسته به qh-common
│     ir.bank.qh.productbuilder.core          Product, ProductVersion, ProductVersionModule,
│                                              ProductRelationship, ProductLegacyMapping
│
├── qh-module-common-rules     02-Common Rules (۹ جدول) — وابسته به core + reference
│     ir.bank.qh.productbuilder.commonrules   ProductEligibilityRule, ProductChannelRule/Operation,
│                                              ProductOrgScope, ProductRequiredDocument/Inquiry,
│                                              ProductPricingRule/Component، ProductRateTier
│
├── qh-module-deposit           03-Deposit Module (۱۳ جدول) — وابسته به core
│     ir.bank.qh.productbuilder.deposit       DepositProductProfile, OpeningRule, TermRule/AllowedTerm,
│                                              TransactionRule, WithdrawalMedia, JointRule, HoldRule,
│                                              DormancyRule, ClosureRule (+Precheck/Settlement/Approval)
│
├── qh-module-loan               04-Loan Module (۶ جدول) — وابسته به core + common-rules + reference
│     ir.bank.qh.productbuilder.loan          LoanProductProfile, EligibilityExtension, FinancialExtension,
│                                              RepaymentRule, ProcessRule, CollateralRule
│
├── qh-module-party             ماژول Party (۱۸ جدول) — فقط وابسته به qh-common (کاملاً مستقل/SOA)
│     ir.bank.qh.party         PartyEntity (والد JOINED) → PersonEntity/OrganizationEntity،
│                              Address, ContactPoint, Identifier, Name, Role, Group/GroupMember,
│                              SignatureSpecimen, Inquiry, Demographic, Country, City, Branch
│
└── qh-app                     اپلیکیشن قابل اجرا؛ همه ماژول‌ها را جمع می‌کند + application.yml + data*.sql
      ir.bank.qh.app
```

پنج ماژول محصول‌ساز همگی زیر یک پکیج ریشه مشترک `ir.bank.qh.productbuilder` هستند (`.reference`،
`.core`، `.commonrules`، `.deposit`، `.loan`) — دقیقاً همان مرز Bounded Context که در بخش
«Domain vs Schema» پایین توضیح داده شده؛ همین یک ریشه مشترک است که باعث می‌شود
`ProductBuilderPersistenceConfig` بتواند با یک `basePackages = "ir.bank.qh.productbuilder"`
همه پنج ماژول را با هم اسکن کند. Party همچنان زیر پکیج مستقل خودش (`ir.bank.qh.party`) است.

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

### Domain vs Schema — دو Bounded Context واقعی، دو دیتابیس مستقل

این پروژه دقیقاً **دو** Bounded Context/دامنه واقعی دارد که هرکدام دیتابیس، Schema، و DataSource/
EntityManagerFactory کاملاً مستقل خودش را دارد:

| دامنه (Persistence Unit) | شامل چه ماژول‌هایی | Schema منطقی |
|---|---|---|
| **Product Builder** | qh-module-reference + qh-module-core + qh-module-common-rules + qh-module-deposit + qh-module-loan (همان ۵ پکیج مدل اصلی، ۴۷ جدول) | `PRODUCTBUILDER` |
| **Party** | qh-module-party (۱۸ جدول، BIAN-style) | `PARTY` |

این پنج پکیج محصول‌ساز صرفاً یک تقسیم‌بندی **سازمانی/کدی** (پنج پکیج جاوا زیر یک دامنه) هستند، نه پنج
Bounded Context جدا — همیشه با هم یک واحد استقرار (Deployment Unit) می‌مانند، پس همه‌شان یک **Schema
واحد** (`PRODUCTBUILDER`) دارند و Entity هایشان آزادانه از `@ManyToOne`/`@JoinColumn` واقعی به هم استفاده
می‌کنند (Hibernate هم FK Constraint واقعی بین‌شان می‌سازد). **Party** تنها مرز واقعی است — از روز اول
کاملاً مستقل/SOA طراحی شده، هیچ Entity ای بین آن و محصول‌ساز رفرنس نمی‌دهد، و به همین دلیل روی
**دیتابیس مستقل خودش** اجرا می‌شود.

اسم فیزیکی هر Schema از `qh.schemas.*` در `application.yml` خوانده می‌شود (مقدار `schema` روی
`@Table` یک نام منطقی ثابت است — چون Hibernate آن را باید در زمان کامپایل به‌صورت constant بداند، نه
از یک property — و توسط `ir.bank.qh.common.config.ConfigurableSchemaNamingStrategy` در لحظه تولید
DDL/SQL به این نام فیزیکی نگاشت می‌شود):

```yaml
qh:
  schemas:
    productbuilder: PRODUCTBUILDER
    party: PARTY
```

### دو DataSource/EntityManagerFactory جدا — آماده جابه‌جایی به دو سرور مستقل

هر Persistence Unit، `DataSource`/`EntityManagerFactory`/`PlatformTransactionManager` کاملاً جدای خودش
را دارد (`ir.bank.qh.app.config.ProductBuilderPersistenceConfig` و `PartyPersistenceConfig`؛
پیکربندی مشترک هر دو — ddl-auto، naming strategy، Tenant Resolver — در `JpaUnitSupport`). به‌صورت
پیش‌فرض این دو، دو دیتابیس **H2 in-memory جدا** هستند:

```yaml
qh:
  datasources:
    productbuilder:
      url: jdbc:h2:mem:qh_productbuilder;DB_CLOSE_DELAY=-1;NON_KEYWORDS=USER
      driver-class-name: org.h2.Driver
      username: sa
      password: ""
    party:
      url: jdbc:h2:mem:qh_party;DB_CLOSE_DELAY=-1;NON_KEYWORDS=USER
      driver-class-name: org.h2.Driver
      username: sa
      password: ""
```

چون هرکدام DataSource واقعاً جدا دارند، جابه‌جایی به دو **سرور کاملاً مستقل** (مثلاً دو Oracle متفاوت)
فقط تغییر `url`/`driver-class-name`/`username`/`password` همین بلوک در `application.yml` یا یک
Profile دیگر است — نیازی به تغییر کد نیست. `application-oracle.yml` یک Profile نمونه است که همین دو
بلوک را روی Oracle نشان می‌دهد (فعلاً بدون Driver واقعی روی classpath — قبل از استفاده واقعی،
`com.oracle.database.jdbc:ojdbc11` را به `qh-app/pom.xml` اضافه کنید و با
`--spring.profiles.active=oracle` فعالش کنید).

⚠️ چون Spring Boot به‌صورت خودکار فقط برای **یک** DataSource می‌تواند JPA را پیکربندی کند
(`spring-boot-hibernate`/`spring-boot-jdbc` autoconfiguration در `QhProductBuilderApplication` عمداً
exclude شده‌اند)، این دو Persistence Unit به‌صورت دستی پیکربندی شده‌اند — شامل Open-Session-In-View
هم برای هر دو جدا (`OpenSessionInViewConfig`) و Seed Data هم برای هر دو جدا (هرکدام seed خودش را فقط
وقتی دیتابیس embedded باشد اجرا می‌کند، پس روی Oracle خودکار غیرفعال است، بدون نیاز به تنظیم دستی).
فایل‌های seed خام (`data-product.sql`, `data-party.sql`) نام Schema را به‌صورت متنی دارند (مثلاً
`INSERT INTO PRODUCTBUILDER.product ...`)؛ اگر Schema پیش‌فرض را عوض کنید باید این‌ها را هم دستی
هماهنگ کنید — محدودیت ذاتی SQL خام است.

`hibernate.hbm2ddl.create_namespaces=true` در `application.yml` باعث می‌شود Hibernate هر دو Schema
فیزیکی را خودش بسازد. اگر جدولی را از طریق SQL خام (نه JPA) کوئری می‌گیرید، حتماً نام Schema فیزیکی
فعلی را هم بنویسید، مثلاً `SELECT * FROM PRODUCTBUILDER.PRODUCT` یا `SELECT * FROM PARTY.PARTY`
(و توجه کنید این دو از حالا واقعاً روی دو Connection/دیتابیس متفاوت‌اند — یک کوئری SQL نمی‌تواند
بینشان JOIN بزند؛ همان چیزی که حذف `@ManyToOne` بین محصول‌ساز و Party را هم توجیه می‌کرد، البته چنین
رابطه‌ای از ابتدا هم در مدل وجود نداشت).

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
2. پکیج جاوای خود را با زیرپکیج‌های `entity` / `repository` / `service` / `controller` بسازید،
   دقیقاً با همان الگوی ماژول‌های موجود — اسم ریشه پکیج به تصمیم مرحله ۱۰ بستگی دارد
   (`ir.bank.qh.productbuilder.card` اگر بخشی از محصول‌ساز است، یا `ir.bank.qh.card` اگر
   Bounded Context جدید و مستقلی است).
3. Entity های خود را با `extends BaseEntity` **یا** `extends TenantAwareEntity` (هر دو در qh-common
   موجودند) بسازید؛ اگر داده‌تان مختص یک نهاد بانکی است از `TenantAwareEntity` استفاده کنید، وگرنه
   (داده مرجع/ثابت مشترک بین همه نهادها) از `BaseEntity`.
4. Repository را `extends JpaRepository<YourEntity, Long>` بسازید.
5. Service را طبق الگوی A یا B بسازید؛ قواعد کسب‌وکار (معادل CHECK Constraint) را داخلش پیاده کنید.
6. Controller را طبق همان الگو بسازید و `@RequestMapping("/api/v1/card/your-entities")` بگذارید.
7. در `pom.xml` ریشه، `<module>qh-module-card</module>` را اضافه کنید.
8. در `qh-app/pom.xml`، `qh-module-card` را به عنوان `<dependency>` اضافه کنید.
9. اگر داده نمونه دارید، یک `data-card.sql` بسازید.
10. **تصمیم مهم: ماژول جدید به کدام Persistence Unit تعلق دارد؟** (نگاه کنید به بخش
    «Domain vs Schema» بالا)
    - اگر واقعاً بخشی از دامنه محصول‌ساز است (به `ProductVersion`/`Product` وصل می‌شود): پکیج جاوای
      خود را زیر `ir.bank.qh.productbuilder.card` بسازید و از `@Table(schema = "PRODUCTBUILDER",
      name = "...")` استفاده کنید — همین کافی است، چون `ProductBuilderPersistenceConfig` (در
      `qh-app/src/main/java/ir/bank/qh/app/config`) از قبل کل `ir.bank.qh.productbuilder` را
      اسکن می‌کند و نیازی به اضافه‌کردن پکیج جدید نیست. `data-card.sql` را به `data-locations` در
      همان کلاس اضافه کنید.
    - اگر یک Bounded Context کاملاً مستقل و جدید است (نه محصول‌ساز، نه Party): یک Schema منطقی
      جدید در `qh.schemas.*` تعریف کنید و یک `CardPersistenceConfig` جدید بسازید (کپی از
      `PartyPersistenceConfig` با پکیج‌ها و نام Bean های خودش) — همراه با یک ورودی جدید در
      `qh.datasources.*` برای DataSource مستقلش.

همین! نیازی به تغییر `QhProductBuilderApplication` نیست (فقط اگر Persistence Unit جدید ساختید، باید
`OpenSessionInViewConfig` را هم برای آن به‌روزرسانی کنید) چون component-scan روی کل `ir.bank.qh` است
و Service/Controller ماژول جدید را خودکار پیدا می‌کند.

## نکات فنی مهم (طبق راهنمای اصلی)

- کلید خواندن Ruleها همیشه `PRODUCT_VERSION_ID` است، نه `PRODUCT_ID` (هر Rule Entity به
  `ProductVersion` وصل است، نه مستقیم به `Product`).
- `RECORD_VERSION` روی همه Entity ها به‌صورت `@Version` (Optimistic Locking) پیاده شده است.
- Auditing (`CREATED_AT/BY`, `UPDATED_AT/BY`) خودکار توسط Spring Data JPA Auditing انجام می‌شود
  (`JpaAuditingConfig` در `qh-common`؛ در دمو کاربر ثابت `SYSTEM` برگردانده می‌شود — در پروژه
  واقعی باید به Spring Security متصل شود).
- `spring.jpa.open-in-view=true` عمداً فعال است تا لود Lazy روابط هنگام serialize کردن JSON با
  خطا مواجه نشود؛ برای پروژه Production توصیه می‌شود این را با DTO/Projection جایگزین کنید. چون دو
  Persistence Unit جدا داریم، این رفتار به‌صورت دستی در `OpenSessionInViewConfig`
  (`qh-app/src/main/java/ir/bank/qh/app/config`) برای هر دو پیاده شده، نه از طریق auto-configuration
  معمول Spring Boot که فقط برای یک DataSource کار می‌کند.

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
