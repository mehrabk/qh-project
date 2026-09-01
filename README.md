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

دو خانواده کلاس پایه در `ir.bank.qh.common.entity` وجود دارد که هر ماژولی (فعلی یا آینده) می‌تواند
از هرکدام که مناسب‌تر است استفاده کند:

| کلاس پایه | برای چه مواردی | چه ماژول‌هایی امروز استفاده می‌کنند |
|---|---|---|
| `BaseAuditableEntity` / `SoftDeletableEntity` | Audit با `LocalDateTime`، `@Version` روی `Long recordVersion` با مقدار پیش‌فرض جاوایی | core, commonrules, deposit, loan, reference |
| `BaseEntity` / `TenantAwareEntity` | Audit با `java.util.Date`، `@DynamicUpdate`، و پشتیبانی از Multi-Tenancy (`@TenantId`) برای موجودیت‌های وابسته به یک نهاد بانکی (Institution) | party |

`BaseEntity` پایه‌ی عمومی (بدون تنانت) است — مناسب داده‌های مرجع مشترک بین همه‌ی نهادها (مثل `CountryEntity`،
`CityEntity` در ماژول Party). `TenantAwareEntity` یک فیلد `institutionId` با انوتیشن `@TenantId` (قابلیت
جدید Hibernate 6/7) اضافه می‌کند که به‌صورت خودکار و شفاف هر Query را به تنانت جاری فیلتر می‌کند.

### Multi-Tenancy (نهاد بانکی/Institution)

- `ir.bank.qh.common.tenant.TenantContext`: نگه‌دارنده تنانت جاری (`ThreadLocal`)، پیش‌فرض = نهاد `1`.
- `ir.bank.qh.common.tenant.TenantIdentifierResolver`: به Hibernate می‌گوید تنانت جاری کدام است.
- `ir.bank.qh.common.tenant.TenantConfig`: این Resolver را به‌صورت یک Bean اسپرینگی به Hibernate متصل می‌کند.
- `ir.bank.qh.common.tenant.TenantFilter`: هدر HTTP اختیاری `X-Institution-Id` را می‌خواند و برای طول
  درخواست تنظیم می‌کند؛ اگر هدر نباشد، پیش‌فرض نهاد `1` (همان نهادی که در `data-party.sql` seed شده) است.

⚠️ **این یک Scaffold پایه‌ی کاربردی است، نه یک پیاده‌سازی امنیتی کامل.** در پروژه واقعی معمولاً تنانت باید
از Principal احرازهویت‌شده (JWT/Session) استخراج شود، نه از یک هدر خام قابل جعل. همچنین چون امکان `mvn
compile` واقعی در محیط من نبود، توصیه می‌کنم رفتار `@TenantId` + `MULTI_TENANT_IDENTIFIER_RESOLVER` را در
اولین اجرای لوکال با دقت تست کنید (لاگ SQL تولیدشده باید `WHERE INSTITUTION_ID = ?` را روی جداول Party نشان
دهد).

### Schema جدا برای هر ماژول

هر ماژول جداول خودش را در یک Schema اختصاصی H2 نگه می‌دارد (`@Table(schema = "...")`):

| ماژول | Schema |
|---|---|
| qh-module-reference | `REFERENCE` |
| qh-module-core | `CORE` |
| qh-module-common-rules | `COMMONRULES` |
| qh-module-deposit | `DEPOSIT` |
| qh-module-loan | `LOAN` |
| qh-module-party | `PARTY` |

`hibernate.hbm2ddl.create_namespaces=true` در `application.yml` باعث می‌شود Hibernate این Schemaها را
خودش در H2 بسازد. اگر جدولی از ماژول دیگر را از طریق SQL خام (نه JPA) کوئری می‌گیرید، حتماً نام Schema را
هم بنویسید، مثلاً `SELECT * FROM CORE.PRODUCT` یا `SELECT * FROM PARTY.PARTY`.

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
3. Entity های خود را با `extends BaseAuditableEntity` **یا** `extends BaseEntity`/`TenantAwareEntity`
   (هر دو در qh-common موجودند) بسازید؛ اگر داده‌تان مختص یک نهاد بانکی است از `TenantAwareEntity`
   استفاده کنید. برای Schema مستقل، `@Table(schema = "CARD", name = "...")` بگذارید.
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

نکته‌ی اضافه برای ماژول Party: زیرساخت Multi-Tenancy (`@TenantId` + `CurrentTenantIdentifierResolver`)
بر اساس مستندات رسمی Hibernate 6/7 نوشته شده، اما چون امکان اجرای واقعی نداشتم، **حتماً در اولین اجرا
بررسی کنید** که کوئری‌های تولیدشده روی جداول Party واقعاً `WHERE INSTITUTION_ID = ?` را اعمال می‌کنند
(با `show-sql: true` در لاگ قابل مشاهده است).
