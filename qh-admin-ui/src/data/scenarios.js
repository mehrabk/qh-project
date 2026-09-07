import { byEntityName } from './meta';
import { buildExamplePayload } from './exampleValues';

// Each scenario is an ordered list of steps. `build(ctx)` receives the
// results of every previous step (keyed by `saveAs`) plus a `first(entityName)`
// helper that fetches the first already-seeded row of a reference entity
// (every table in this project ships with at least one seed row - see
// qh-app/src/main/resources/data-product.sql / data-party.sql), so scenarios
// don't need to recreate reference data that already exists.

function autoPayload(entityName, overrides = {}) {
  const entity = byEntityName[entityName];
  const skip = new Set(Object.keys(overrides));
  const payload = buildExamplePayload(entity, skip);
  return { ...payload, ...overrides };
}

export const scenarios = [
  {
    id: 'deposit-product',
    title: 'سناریوی کامل محصول سپرده',
    description:
      'یک محصول جدید می‌سازد، یک نسخه برای آن منتشر می‌کند و قواعد افتتاح/مدت سپرده را روی همان نسخه تعریف می‌کند — دقیقاً زنجیره‌ای که در Domain vs Schema پروژه توضیح داده شده: Product → ProductVersion → Deposit Rules.',
    boundedContext: 'productbuilder',
    steps: [
      {
        label: 'ایجاد محصول (Product)',
        saveAs: 'product',
        entity: 'Product',
        build: () => autoPayload('Product', { productClassCode: 'DEPOSIT', balanceNatureCode: 'LIABILITY' }),
      },
      {
        label: 'انتشار نسخه محصول (ProductVersion)',
        saveAs: 'productVersion',
        entity: 'ProductVersion',
        build: (ctx) => autoPayload('ProductVersion', { product: { id: ctx.product.id } }),
      },
      {
        label: 'تعریف پروفایل سپرده (DepositProductProfile)',
        saveAs: 'depositProfile',
        entity: 'DepositProductProfile',
        build: (ctx) => autoPayload('DepositProductProfile', { productVersion: { id: ctx.productVersion.id } }),
      },
      {
        label: 'تعریف قاعده افتتاح حساب (DepositProductOpeningRule)',
        saveAs: 'openingRule',
        entity: 'DepositProductOpeningRule',
        build: (ctx) => autoPayload('DepositProductOpeningRule', { productVersion: { id: ctx.productVersion.id } }),
      },
      {
        label: 'تعریف قاعده مدت سپرده (DepositProductTermRule)',
        saveAs: 'termRule',
        entity: 'DepositProductTermRule',
        build: (ctx) => autoPayload('DepositProductTermRule', { productVersion: { id: ctx.productVersion.id } }),
      },
    ],
  },
  {
    id: 'loan-product',
    title: 'سناریوی کامل محصول تسهیلات',
    description:
      'یک محصول تسهیلاتی می‌سازد و آن را با داده‌های مرجع از پیش موجود (نوع تسهیلات، نوع طرح، کاربری) در ماژول Reference ترکیب می‌کند، سپس قاعده بازپرداخت را تعریف می‌کند: Product → ProductVersion → LoanProductProfile (+Reference) → LoanProductRepaymentRule.',
    boundedContext: 'productbuilder',
    steps: [
      {
        label: 'ایجاد محصول (Product)',
        saveAs: 'product',
        entity: 'Product',
        build: () => autoPayload('Product', { productClassCode: 'LOAN', balanceNatureCode: 'ASSET' }),
      },
      {
        label: 'انتشار نسخه محصول (ProductVersion)',
        saveAs: 'productVersion',
        entity: 'ProductVersion',
        build: (ctx) => autoPayload('ProductVersion', { product: { id: ctx.product.id } }),
      },
      {
        label: 'اتصال به داده مرجع و تعریف پروفایل تسهیلات (LoanProductProfile)',
        saveAs: 'loanProfile',
        entity: 'LoanProductProfile',
        build: async (ctx, { first }) => {
          const [loanType, planType, loanUsage] = await Promise.all([
            first('LoanType'), first('PlanType'), first('LoanUsage'),
          ]);
          return autoPayload('LoanProductProfile', {
            productVersion: { id: ctx.productVersion.id },
            loanType: { id: loanType.id },
            planType: { id: planType.id },
            loanUsage: { id: loanUsage.id },
            economicSubSection: null,
            facilityOperationParameter: null,
          });
        },
      },
      {
        label: 'تعریف قاعده بازپرداخت (LoanProductRepaymentRule)',
        saveAs: 'repaymentRule',
        entity: 'LoanProductRepaymentRule',
        build: (ctx) => autoPayload('LoanProductRepaymentRule', { productVersion: { id: ctx.productVersion.id } }),
      },
    ],
  },
  {
    id: 'party-onboarding',
    title: 'سناریوی ثبت شخص حقیقی جدید (Party Onboarding)',
    description:
      'مطابق مدل EA بازنویسی‌شده Party: ابتدا هسته مرکزی PartyEntity ساخته می‌شود، سپس PersonEntity با FK به آن متصل می‌شود (PERSON دیگر زیرکلاس ارث‌بری‌شده از Party نیست - جدول جزئیات مستقلی با FK یک‌به‌یک است)، آنگاه PARTY_MEMBERSHIP مرز صندوق را می‌سازد که آدرس و شماره تماس روی آن تعریف می‌شوند: Party → Person, Party → PartyMembership → PartyAddress / ContactPoint.',
    boundedContext: 'party',
    steps: [
      {
        label: 'ایجاد هسته مرکزی پارتی (PartyEntity)',
        saveAs: 'party',
        entity: 'PartyEntity',
        build: () => autoPayload('PartyEntity', {
          partyType: { id: 'PERSON' },
          verificationStatus: { id: 'PENDING' },
          dataSource: { id: 'DIGITAL_ONBOARDING' },
        }),
      },
      {
        label: 'ثبت جزئیات شخص حقیقی (PersonEntity)',
        saveAs: 'person',
        entity: 'PersonEntity',
        build: (ctx) => autoPayload('PersonEntity', {
          party: { id: ctx.party.id },
          gender: { id: 'MALE' },
          maritalStatus: { id: 'SINGLE' },
          residenceStatus: { id: 'RESIDENT' },
          birthCountry: { id: 'IRN' },
          birthPlace: null,
        }),
      },
      {
        label: 'ایجاد عضویت صندوقی (PartyMembershipEntity)',
        saveAs: 'membership',
        entity: 'PartyMembershipEntity',
        build: (ctx) => autoPayload('PartyMembershipEntity', { party: { id: ctx.party.id } }),
      },
      {
        label: 'اتصال آدرس موجود به عضویت (PartyAddressEntity)',
        saveAs: 'partyAddress',
        entity: 'PartyAddressEntity',
        build: async (ctx, { first }) => {
          const address = await first('AddressEntity');
          return autoPayload('PartyAddressEntity', {
            partyMembership: { id: ctx.membership.id },
            address: { id: address.id },
            addressType: { id: 'RESIDENTIAL' },
          });
        },
      },
      {
        label: 'ثبت شماره تماس برای عضویت (ContactPointEntity)',
        saveAs: 'contactPoint',
        entity: 'ContactPointEntity',
        build: (ctx) => autoPayload('ContactPointEntity', {
          partyMembership: { id: ctx.membership.id },
          contactType: { id: 'MOBILE_PHONE' },
          purpose: { id: 'PERSONAL' },
        }),
      },
    ],
  },
];
