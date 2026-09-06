# محصول‌ساز یکپارچه قرض‌الحسنه — Modular Monolith (SOA-Style)

پیاده‌سازی Spring Boot برای مدل داده «محصول‌ساز یکپارچه سپرده و تسهیلات قرض‌الحسنه» (۴۷ جدول در ۵ زیرپکیج
محصول‌ساز: Core, Common Rules, Deposit, Loan, Reference) به‌همراه ماژول Party مستقل (۱۷ جدول، معماری
BIAN-style) — مجموعاً ۶ قابلیت کسب‌وکاری در ۲ Bounded Context/Persistence Unit واقعی.
معماری: **Modular Monolith** — هر Bounded Context (محصول‌ساز، Party) با Schema/دیتابیس اختصاصی خودش
زندگی می‌کند (Entity/Repository/Service/Controller مستقل). محصول‌ساز خودش یک ماژول Maven **والد**
(`qh-module-productbuilder`، `packaging=pom`) است که پنج زیرماژول Maven جدا (core/reference/
commonrules/deposit/loan) را زیر خودش گروه‌بندی می‌کند؛ اما همه با هم در یک اپلیکیشن Spring Boot
واحد (`qh-app`) اجرا می‌شوند. این یعنی می‌توانید هر Bounded Context را در آینده به یک میکروسرویس مستقل
تبدیل کنید بدون این‌که لازم باشد کد را از نو بازنویسی کنید — همان کاری که برای Party (و در آینده برای
کل محصول‌ساز) با دو دیتابیس مستقل از قبل آماده شده (بخش «Domain vs Schema» را ببینید).

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
├── qh-common                    مشترک: BaseEntity، Auditing، Exception Handler، ApiResponse
│
├── qh-module-productbuilder     دامنه محصول‌ساز (pom والد، ۴۷ جدول، یک Schema/دیتابیس، پنج زیرماژول Maven)
│     ├── reference (qh-module-productbuilder-reference)   05-Reference Data (۱۴ جدول): DocumentType,
│     │     ir.bank.qh.productbuilder.reference             LoanType, PlanType, LoanUsage,
│     │                                                      EconomicSection/SubSection,
│     │                                                      Operation/SubOperation, PatternOperation*،
│     │                                                      LoanProductCollateral، ...
│     ├── core (qh-module-productbuilder-core)              01-Core (۵ جدول): Product, ProductVersion,
│     │     ir.bank.qh.productbuilder.core                  ProductVersionModule, ProductRelationship,
│     │                                                      ProductLegacyMapping
│     ├── commonrules (qh-module-productbuilder-commonrules) 02-Common Rules (۹ جدول): ProductEligibilityRule,
│     │     ir.bank.qh.productbuilder.commonrules           ProductChannelRule/Operation, ProductOrgScope,
│     │                                                      ProductRequiredDocument/Inquiry,
│     │                                                      ProductPricingRule/Component، ProductRateTier
│     ├── deposit (qh-module-productbuilder-deposit)        03-Deposit Module (۱۳ جدول): DepositProductProfile,
│     │     ir.bank.qh.productbuilder.deposit               OpeningRule, TermRule/AllowedTerm, TransactionRule,
│     │                                                      WithdrawalMedia, JointRule, HoldRule, DormancyRule,
│     │                                                      ClosureRule (+Precheck/Settlement/Approval)
│     └── loan (qh-module-productbuilder-loan)               04-Loan Module (۶ جدول): LoanProductProfile,
│           ir.bank.qh.productbuilder.loan                  EligibilityExtension, FinancialExtension,
│                                                             RepaymentRule, ProcessRule, CollateralRule
│
├── qh-module-party             ماژول Party (۱۷ جدول) — فقط وابسته به qh-common (کاملاً مستقل/SOA)
│     ir.bank.qh.party         PartyEntity (والد JOINED) → PersonEntity/OrganizationEntity،
│                              Address, ContactPoint, Identifier, Name, Role, Group/GroupMember,
│                              SignatureSpecimen, Inquiry, Demographic, Country, City
│
└── qh-app                     اپلیکیشن قابل اجرا؛ همه ماژول‌ها را جمع می‌کند + application.yml + data*.sql
      ir.bank.qh.app
```

این پنج زیردامنه محصول‌ساز چون همیشه با هم Deploy/Version می‌شوند (همان مرز Bounded Context که در بخش
«Domain vs Schema» پایین توضیح داده شده)، به‌جای پنج ماژول مستقل و هم‌سطح در ریشه پروژه، به‌صورت پنج
**زیرماژول Maven تودرتو (nested)** زیر یک ماژول **والد** گروه‌بندی شده‌اند: `qh-module-productbuilder`
خودش `packaging=pom` دارد و صرفاً یک aggregator/parent POM است (کد جاوا ندارد)، و پنج فرزندش —
`reference`، `core`، `commonrules`، `deposit`، `loan` — هرکدام یک ماژول Maven مستقل با
`packaging=jar` و artifactId خودشان (`qh-module-productbuilder-reference` و ...) هستند که در
`<modules>` همان pom والد لیست شده‌اند. گراف وابستگی بین این پنج زیرماژول همان گراف منطقی دامنه است:
`reference` و `core` فقط به `qh-common` وابسته‌اند؛ `commonrules` به `core`+`reference`؛ `deposit` به
`core`؛ و `loan` به `core`+`commonrules`+`reference`. کد جاوای هرکدام زیر پکیج‌های جدا
(`ir.bank.qh.productbuilder.reference`، `.core`، `.commonrules`، `.deposit`، `.loan`) سازمان‌دهی شده؛
همین ریشه مشترک `ir.bank.qh.productbuilder` است که به `ProductBuilderPersistenceConfig` اجازه می‌دهد
با یک `basePackages = "ir.bank.qh.productbuilder"` همه پنج زیرماژول را با هم اسکن کند و روی یک
Schema/دیتابیس مشترک (`PRODUCTBUILDER`) بنشاند. Party همچنان ماژول Maven و پکیج مستقل خودش
(`ir.bank.qh.party`) است و هیچ زیرماژولی ندارد.

هر ماژول یک ساختار پکیج یکسان دارد:

```
ir.bank.qh.<module>
├── entity/       @Entity ها (JPA)
├── repository/   Spring Data JPA (JpaRepository)
├── service/      منطق کسب‌وکار
└── controller/   کنترلر REST
```

**نکته مهم درباره ماژول Party:** ۵ ماژول محصول‌ساز، Service خود را از `AbstractCrudService` (در
qh-common) ارث می‌برند و فقط دو متد `beforeCreate`/`beforeUpdate` را در صورت نیاز override می‌کنند؛ اما
طبق درخواست شما **ماژول Party کاملاً مستقل و بدون وراثت از این کلاس عمومی** پیاده‌سازی شده — هر Service
تمام متدهای CRUD خودش را به‌صورت مستقیم و صریح دارد. این با هدف استقلال کامل ماژول Party (آماده جداشدن
به یک سرویس مجزا در آینده) هم‌خوانی دارد.

**نکته مهم درباره Controller ها:** دیگر هیچ Controller ای (نه در محصول‌ساز، نه در Party) از یک کلاس پایه
مشترک ارث نمی‌برد؛ `AbstractCrudController` حذف شده و همهٔ Controller ها (در هر دو ماژول) متدهای
`@GetMapping`/`@PostMapping`/`@PutMapping`/`@DeleteMapping` را به‌صورت صریح خودشان دارند و مستقیماً به
لایه Service تفویض می‌کنند.

### پایه‌های مشترک Entity ها (qh-common)

یک خانواده کلاس پایه در `ir.bank.qh.common.entity` وجود دارد و همه ماژول‌ها (core, commonrules,
deposit, loan, reference, party) از همین سلسله‌مراتب استفاده می‌کنند:

| کلاس پایه | برای چه مواردی | چه ماژول‌هایی استفاده می‌کنند |
|---|---|---|
| `BaseEntity` | Audit (`CREATED_BY/ON`, `MODIFIED_BY/ON`) با `java.util.Date`، `@DynamicUpdate`، و `@Version` روی `Long version` — بدون تنانت | داده‌های مرجع/ثابت مشترک بین همه نهادها: reference (کل ماژول)، و `CountryEntity`/`CityEntity`/`AddressEntity` در party |
| `TenantAwareEntity extends BaseEntity` | همان Audit به‌علاوه فیلد `institutionId` با انوتیشن `@TenantId` (قابلیت Hibernate 6/7) که به‌صورت خودکار و شفاف هر Query را به تنانت جاری فیلتر می‌کند | موجودیت‌های وابسته به یک نهاد بانکی: core, commonrules, deposit, loan (کل محصول‌ساز) و بیشتر Entity های party (Party, ContactPoint, ...) |

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
| **Product Builder** | qh-module-productbuilder (pom والد) + ۵ زیرماژول reference/core/commonrules/deposit/loan (۴۷ جدول) | `PRODUCTBUILDER` |
| **Party** | qh-module-party (۱۷ جدول، BIAN-style) | `PARTY` |

این پنج زیرماژول محصول‌ساز صرفاً یک تقسیم‌بندی **سازمانی/کدی** (پنج ماژول Maven تودرتو زیر یک دامنه)
هستند، نه پنج Bounded Context جدا — همیشه با هم یک واحد استقرار (Deployment Unit) می‌مانند، پس همه‌شان
یک **Schema واحد** (`PRODUCTBUILDER`) دارند و Entity هایشان آزادانه از `@ManyToOne`/`@JoinColumn` واقعی به هم استفاده
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
`AbstractCrudService` (در qh-common) ارث می‌برد و فقط دو متد `beforeCreate`/`beforeUpdate` را در
صورت نیاز override می‌کند؛ Controller به‌صورت صریح نوشته می‌شود (متدهای استاندارد CRUD را مستقیم
تعریف می‌کند و به Service تفویض می‌کند). برای CRUD ساده مناسب است.

**الگوی B — کاملاً مستقل (مثل party):** Service هم از کلاس عمومی ارث نمی‌برد؛ تمام متدهای CRUD
به‌صورت صریح در خود کلاس Service نوشته می‌شوند (Controller در این الگو هم مثل الگوی A صریح است).
برای ماژولی که قرار است بعداً به یک سرویس مجزا (میکروسرویس) جدا شود یا منطق کسب‌وکار پیچیده‌تری
دارد مناسب‌تر است.

فرض کنید می‌خواهید قابلیت جدید «کارت بانکی» را اضافه کنید. **اولین تصمیم:** آیا این واقعاً بخشی از
دامنه محصول‌ساز است (به `ProductVersion`/`Product` وصل می‌شود) یا یک Bounded Context کاملاً مستقل و
جدید (نه محصول‌ساز، نه Party)؟ این تصمیم مسیر را کاملاً عوض می‌کند:

**حالت ۱ — بخشی از محصول‌ساز است (رایج‌ترین حالت):** نیازی به Bounded Context جدید نیست؛ دو زیرحالت دارد:

*۱-الف) اگر قابلیت جدید به یکی از زیردامنه‌های موجود نزدیک است* (مثلاً بخشی از قواعد Loan)، کافی است
داخل همان زیرماژول Maven موجود (مثلاً `qh-module-productbuilder/loan`) یک زیرپکیج جدید اضافه کنید و
نیازی به تغییر هیچ `pom.xml` ای نیست.

*۱-ب) اگر قابلیت جدید یک زیردامنه کاملاً تازه داخل محصول‌ساز است* (مثل «کارت بانکی») یک **زیرماژول
Maven جدید** زیر `qh-module-productbuilder` بسازید، دقیقاً با همان الگوی پنج زیرماژول موجود:

1. یک پوشه جدید بسازید: `qh-module-productbuilder/card/pom.xml` — `parent` را
   `qh-module-productbuilder` بگذارید، `artifactId` را `qh-module-productbuilder-card`،
   `packaging` را `jar`، و وابستگی‌ها را طبق نیاز (حداقل `qh-common`؛ اگر به Core وصل می‌شود
   `qh-module-productbuilder-core` را هم اضافه کنید — دقیقاً مثل `deposit`/`loan`).
2. در `qh-module-productbuilder/pom.xml` (pom والد)، `<module>card</module>` را به `<modules>`
   اضافه کنید.
3. در ریشه `pom.xml`، یک ورودی `qh-module-productbuilder-card` به `dependencyManagement` اضافه کنید.
4. در `qh-app/pom.xml`، `qh-module-productbuilder-card` را به عنوان `<dependency>` اضافه کنید.
5. پکیج `ir.bank.qh.productbuilder.card` را با زیرپکیج‌های `entity` / `repository` / `service` /
   `controller` بسازید (در `qh-module-productbuilder/card/src/main/java/...`)، دقیقاً با همان الگوی
   زیرماژول‌های موجود (`.reference`, `.core`, `.commonrules`, `.deposit`, `.loan`).
6. Entity های خود را با `extends BaseEntity` **یا** `extends TenantAwareEntity` (هر دو در qh-common
   موجودند) و `@Table(schema = "PRODUCTBUILDER", name = "...")` بسازید.
7. Repository را `extends JpaRepository<YourEntity, Long>` بسازید.
8. Service را طبق الگوی A یا B بسازید؛ قواعد کسب‌وکار (معادل CHECK Constraint) را داخلش پیاده کنید.
9. Controller را طبق همان الگو بسازید و `@RequestMapping("/api/v1/card/your-entities")` بگذارید.
10. اگر داده نمونه دارید، یک `data-card.sql` بسازید و به `data-locations` در
    `ProductBuilderPersistenceConfig` (`qh-app/src/main/java/ir/bank/qh/app/config`) اضافه کنید.

نیازی به تغییر `ProductBuilderPersistenceConfig` (به‌جز مرحله ۱۰) نیست — چون با یک
`basePackages = "ir.bank.qh.productbuilder"` کل درخت (هر تعداد زیرماژول که باشد) را اسکن می‌کند.

**حالت ۲ — Bounded Context کاملاً مستقل و جدید است:** مثل الگوی Party عمل کنید:

1. یک پوشه Maven جدید بسازید: `qh-module-card/pom.xml` (کپی از `qh-module-party`، وابستگی فقط به
   `qh-common`).
2. پکیج `ir.bank.qh.card` را با زیرپکیج‌های `entity` / `repository` / `service` / `controller`
   بسازید — Service/Controller طبق الگوی B (مستقل، مثل party).
3. در `pom.xml` ریشه، `<module>qh-module-card</module>` را اضافه کنید.
4. در `qh-app/pom.xml`، `qh-module-card` را به عنوان `<dependency>` اضافه کنید.
5. یک Schema منطقی جدید در `qh.schemas.*` و یک ورودی جدید در `qh.datasources.*` تعریف کنید.
6. یک `CardPersistenceConfig` جدید بسازید (کپی از `PartyPersistenceConfig` با پکیج‌ها و نام
   Bean‌های خودش)، و `OpenSessionInViewConfig` را برای این Persistence Unit جدید هم به‌روزرسانی
   کنید.
7. اگر داده نمونه دارید، یک `data-card.sql` بسازید و آن را در `CardPersistenceConfig`'s
   initializer اضافه کنید.

در هر دو حالت نیازی به تغییر `QhProductBuilderApplication` نیست، چون component-scan روی کل
`ir.bank.qh` است و Service/Controller قابلیت جدید را خودکار پیدا می‌کند.

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
