# qh-admin-ui

پنل مدیریت React مستقل برای پروژه **qh-project** (محصول‌ساز یکپارچه قرض‌الحسنه). این یک پروژه Node/Vite
جداگانه است که در همین ریپازیتوری، در پوشه‌ی خودش (`qh-admin-ui/`) نگهداری می‌شود؛ به هیچ ماژول Maven
دیگری وابسته نیست و مستقیماً روی REST API های بک‌اند (از طریق پراکسی Vite به `localhost:8080`) کار می‌کند.

## اجرا

پیش‌نیاز: بک‌اند Spring Boot پروژه اصلی باید روی پورت `8080` در حال اجرا باشد:

```bash
cd /path/to/qh-project/qh-app
mvn spring-boot:run
```

سپس در این پوشه:

```bash
npm install
npm run dev
```

آدرس `http://localhost:5173` را باز کنید. تمام درخواست‌های `/api/**` توسط تنظیمات پراکسی Vite
(`vite.config.js`) به `http://localhost:8080` فوروارد می‌شوند — نیازی به تنظیم CORS در بک‌اند نیست.

## چه چیزی پوشش داده شده؟

- تمام ۶۴ موجودیت پروژه (۴۷ جدول محصول‌ساز + ۱۷ جدول Party) با CRUD کامل، تولیدشده از متادیتای واقعی
  entity/controller های بک‌اند (`src/data/entitiesMeta.json`، خروجی یک اسکریپت پارسر پایتون که مستقیماً
  کد جاوا را خوانده — نه دستی نوشته‌شده).
- فرم‌های داینامیک با ورودی مناسب برای هر نوع فیلد (متن/عدد/تاریخ/چک‌باکس/select برای enum/dropdown
  برای کلید خارجی) + دکمه «پر کردن با مقادیر نمونه».
- سه سناریوی نمونه اجرای زنده (صفحه اصلی): محصول سپرده کامل، محصول تسهیلات کامل، ثبت شخص جدید.
- تمام جدول‌ها از قبل داده seed واقعی بک‌اند را نشان می‌دهند (هر ۶۴ جدول حداقل یک ردیف نمونه دارد).

## باگ‌های بک‌اند که در همین راند پیدا و رفع شدند

تست این UI روی سناریوهای واقعی چند باگ پیش‌موجود در بک‌اند (خارج از این رابط کاربری) را آشکار کرد که
به همراه آن اصلاح شدند:

1. **هر PUT و هر POST با فیلد `@ManyToOne`** با خطای Hibernate
   `Detached entity ... has an uninitialized version value` شکست می‌خورد — چون `version` در
   `BaseEntity` با `@JsonIgnore` از JSON مخفی است ولی سرویس‌ها مستقیماً شیء deserialize‌شده کلاینت را
   `save()` می‌کردند. رفع شد با `JpaReferenceResolver` (تبدیل رفرنس‌های "فقط-id" به رفرنس مدیریت‌شده
   Hibernate از طریق `EntityManager.getReference`) + بازنویسی امن‌تر `update()` در
   `AbstractCrudService` و همه سرویس‌های Party.
2. **فیلد `party` روی ۹ موجودیت Party** (`PartyAddressEntity`، `ContactPointEntity`، ...) با
   `@JsonIgnore` علامت خورده بود تا از حلقه بی‌نهایت سریالایز جلوگیری کند، اما همین annotation
   دیسریالایز شدن آن را هم کاملاً مسدود می‌کرد. رفع شد با تغییر به
   `@JsonProperty(access = WRITE_ONLY)` (قابل نوشتن از JSON، ولی هرگز در پاسخ برنمی‌گردد).
3. **شناسه‌های auto-increment در ۴۳ جدول محصول‌ساز** بعد از seed با شماره‌های پایین (۱، ۲، ...) شروع
   می‌شدند بدون `ALTER TABLE ... RESTART WITH`، پس اولین رکورد جدید همیشه با رکورد seed تصادم
   می‌کرد. رفع شد با افزودن `RESTART WITH` مناسب برای هر ۴۳ جدول در `data-product.sql`.
4. **کپی بی‌رویه فیلدها در update** باعث می‌شد کالکشن‌های مدیریت‌شده توسط Hibernate (مثل
   `PartyEntity.addresses`) با یک لیست خالی جایگزین شوند و خطای
   `A collection with orphan deletion was no longer referenced` بدهند. رفع شد با
   `JpaReferenceResolver.updateIgnoredProperties()` که فیلدهای `@OneToMany`/`@ManyToMany`/
   inverse-`@OneToOne` را از کپی مستثنی می‌کند.

هر سه سناریوی صفحه اصلی (محصول سپرده کامل، محصول تسهیلات کامل، ثبت شخص جدید) و CRUD کامل (create/update/delete)
روی هر دو bounded context اکنون end-to-end تست و تأیید شده‌اند.
