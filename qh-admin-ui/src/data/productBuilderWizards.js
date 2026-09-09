// Guided, multi-step "author a product" wizards for the Product Builder
// bounded context, following the same BIAN-style lifecycle already enforced
// by the backend (see ProductVersionService/ProductVersionController):
//
//   Product (the general template)
//     -> ProductVersion, starts DRAFT / origination+servicing DISABLED
//       -> Profile + Rules authored on that version
//       -> approve()            DRAFT      -> APPROVED
//       -> enableOrigination()  APPROVED   -> ORIGINATION/SERVICING = ENABLED, isCurrent = true
//
// This intentionally does NOT touch scenarios.js/ScenarioRunner.jsx (the
// existing "deposit-product"/"loan-product" auto-payload smoke tests stay
// exactly as they are) - it's a separate, additive way to actually author a
// product with real values and see the same business rules the backend
// enforces (min/max ordering, DEPOSIT<->LIABILITY / LOAN<->ASSET, origination
// only after approval, ...) explained and checked as you go.
//
// Step shape mirrors wizards.js (see Wizard.jsx), plus two additions used
// here:
//   validate(values, fixed, ctx) -> string | null   - client-side business
//     rule check run right before the create call; a returned string blocks
//     submission and is shown as the step's error, mirroring the backend's
//     own BusinessRuleViolationException checks so the user sees the same
//     rule instantly instead of after a round trip.
//   kind: 'action'   - not a create step: renders an explanation + a single
//     button that calls step.run(ctx) (a lifecycle-transition endpoint, e.g.
//     POST .../approve) instead of POSTing a new entity.

import { api } from '../api/client';
import { byEntityName } from './meta';

function minMax(values, minField, maxField, label) {
  const min = values[minField];
  const max = values[maxField];
  if (min != null && max != null && Number(min) > Number(max)) {
    return `${label}: مقدار حداقل نمی‌تواند از حداکثر بیشتر باشد`;
  }
  return null;
}

function dateOrder(values, fromField, toField, label) {
  const from = values[fromField];
  const to = values[toField];
  if (from && to && from > to) {
    return `${label}: تاریخ شروع نمی‌تواند بعد از تاریخ پایان باشد`;
  }
  return null;
}

function versionPath() {
  return byEntityName['ProductVersion'].path;
}

const versionStep = {
  key: 'version',
  entity: 'ProductVersion',
  label: 'نسخه اولیه محصول (Product Version)',
  help:
    'در BIAN، تعریف محصول (بالا) از نسخه‌های آن جدا است - قوانین و پارامترهای واقعی همیشه روی یک نسخه ثبت می‌شوند، نه روی خود محصول. نسخه تازه‌ساز همیشه DRAFT است و افتتاح/سرویس‌دهی روی آن غیرفعال است تا وقتی تصویب و فعال شود.',
  fixedValues: (ctx) => ({
    product: { id: ctx.product.id },
    versionStatusCode: 'DRAFT',
    originationStatusCode: 'DISABLED',
    servicingStatusCode: 'DISABLED',
    recordStatusCode: 'ACTIVE',
    isCurrent: false,
  }),
  validate: (values) => dateOrder(values, 'validFrom', 'validTo', 'بازه اعتبار نسخه'),
};

const approveStep = {
  key: 'approveVersion',
  kind: 'action',
  entity: 'ProductVersion',
  label: 'تصویب نسخه (DRAFT ← APPROVED)',
  help:
    'مرحله «اعتبارسنجی و تصویب» چرخه حیات محصول - از این پس دیگر نمی‌توان قوانین این نسخه را عوض کرد، فقط می‌توان نسخه جدیدی از روی آن ساخت.',
  summary: (ctx) => `نسخه شماره ${ctx.version.versionNo ?? '#' + ctx.version.id} از وضعیت DRAFT به APPROVED منتقل می‌شود.`,
  run: (ctx) => api.action(`${versionPath()}/${ctx.version.id}/approve`),
};

const enableOriginationStep = {
  key: 'enableOrigination',
  kind: 'action',
  entity: 'ProductVersion',
  label: 'فعال‌سازی افتتاح/اعطا (APPROVED ← ENABLED)',
  help:
    'آخرین گام: از این پس صندوق‌ها می‌توانند بر اساس این نسخه، حساب/تسهیلات واقعی برای مشتری باز کنند. این نسخه هم‌زمان به‌عنوان نسخه جاری محصول (IS_CURRENT) هم علامت می‌خورد.',
  summary: (ctx) => `افتتاح (Origination) و سرویس‌دهی (Servicing) برای نسخه ${ctx.version.versionNo ?? '#' + ctx.version.id} فعال می‌شود.`,
  run: (ctx) => api.action(`${versionPath()}/${ctx.version.id}/enable-origination`),
};

export const productBuilderWizards = [
  {
    id: 'deposit-product-authoring',
    title: 'طراحی محصول سپرده (Deposit Product)',
    description:
      'مسیر واقعی طراحی یک محصول سپرده به سبک بانکداری BIAN: محصول → نسخه → پروفایل سپرده → قواعد افتتاح/مدت/تراکنش/بستن → نرخ‌گذاری و کانال (اختیاری) → تصویب → فعال‌سازی. سناریوهای نمونه پایین صفحه دست‌نخورده باقی می‌مانند؛ این بخش برای واردکردن مقادیر واقعی خودتان است.',
    boundedContext: 'productbuilder',
    steps: [
      {
        key: 'product',
        entity: 'Product',
        label: '۱. تعریف کلی محصول (Product)',
        help:
          'PRODUCT فقط «قالب» محصول است (مثل «سپرده کوتاه‌مدت») - هنوز پارامتر و قانونی روی آن نیست. نوع تراز حسابداری طبق استاندارد، برای محصول سپرده همیشه LIABILITY (بدهی بانک به مشتری) است و به‌صورت خودکار قفل شده.',
        fixedValues: () => ({ productClassCode: 'DEPOSIT', balanceNatureCode: 'LIABILITY' }),
      },
      { ...versionStep },
      {
        key: 'profile',
        entity: 'DepositProductProfile',
        label: '۳. پروفایل سپرده (نوع/گروه)',
        help: 'گروه و نوع سپرده را طبق دسته‌بندی داخلی بانک وارد کنید (مثلاً گروه SHORT_TERM، نوع QARZ_AL_HASANEH).',
        fixedValues: (ctx) => ({ productVersion: { id: ctx.version.id } }),
      },
      {
        key: 'openingRule',
        entity: 'DepositProductOpeningRule',
        label: '۴. قاعده افتتاح حساب',
        help: 'حداقل/حداکثر مبلغ افتتاح و موجودی الزامی این محصول.',
        fixedValues: (ctx) => ({ productVersion: { id: ctx.version.id } }),
        validate: (values) =>
          minMax(values, 'minOpeningAmount', 'maxOpeningAmount', 'مبلغ افتتاح') ||
          minMax(values, 'minRequiredBalance', 'maxAllowedBalance', 'موجودی الزامی/مجاز'),
      },
      {
        key: 'termRule',
        entity: 'DepositProductTermRule',
        label: '۵. قاعده مدت سپرده (اختیاری)',
        help: 'برای سپرده‌های مدت‌دار (کوتاه/بلندمدت) - سپرده قرض‌الحسنه معمولاً این مرحله را رد می‌کند.',
        optional: true,
        fixedValues: (ctx) => ({ productVersion: { id: ctx.version.id }, ruleStatusCode: 'ACTIVE' }),
        validate: (values) => minMax(values, 'minTermValue', 'maxTermValue', 'مدت سپرده'),
      },
      {
        key: 'transactionRules',
        entity: 'DepositProductTransactionRule',
        label: '۶. قواعد تراکنش - واریز/برداشت (اختیاری، تکرارپذیر)',
        help: 'برای هرکدام از انواع تراکنش (واریز، برداشت) یک ردیف جداگانه با سقف‌های خودش تعریف کنید.',
        optional: true,
        repeatable: true,
        fixedValues: (ctx) => ({ productVersion: { id: ctx.version.id } }),
        validate: (values) =>
          minMax(values, 'minTransactionAmount', 'maxTransactionAmount', 'مبلغ تراکنش'),
      },
      {
        key: 'closureRule',
        entity: 'DepositProductClosureRule',
        label: '۷. قاعده بستن حساب (اختیاری)',
        optional: true,
        fixedValues: (ctx) => ({ productVersion: { id: ctx.version.id }, statusCode: 'ACTIVE' }),
        validate: (values) => dateOrder(values, 'effectiveFromDate', 'effectiveToDate', 'بازه اعتبار قاعده بستن'),
      },
      {
        key: 'pricingRule',
        entity: 'ProductPricingRule',
        label: '۸. نرخ سود/جایزه سپرده (اختیاری)',
        help: 'قواعد قیمت‌گذاری بین محصول سپرده و محصول تسهیلات مشترک است (ProductPricingRule) - همان جدول برای هر دو استفاده می‌شود.',
        optional: true,
        fixedValues: (ctx) => ({ productVersion: { id: ctx.version.id }, ruleStatusCode: 'ACTIVE' }),
        validate: (values) => dateOrder(values, 'validFrom', 'validTo', 'بازه اعتبار نرخ'),
      },
      {
        key: 'channelRules',
        entity: 'ProductChannelRule',
        label: '۹. کانال‌های ارائه (اختیاری، تکرارپذیر)',
        help: 'این محصول از چه کانال‌هایی (شعبه، موبایل بانک، اینترنت بانک) قابل افتتاح است.',
        optional: true,
        repeatable: true,
        fixedValues: (ctx) => ({ productVersion: { id: ctx.version.id }, ruleStatusCode: 'ACTIVE' }),
      },
      {
        key: 'eligibilityRule',
        entity: 'ProductEligibilityRule',
        label: '۱۰. شرایط احراز مشتری (اختیاری)',
        help: 'محدودیت‌های سن، نوع شخص، سطح احراز هویت (KYC) و ریسک AML برای این محصول.',
        optional: true,
        fixedValues: (ctx) => ({ productVersion: { id: ctx.version.id }, ruleStatusCode: 'ACTIVE' }),
        validate: (values) => minMax(values, 'minAge', 'maxAge', 'سن مشتری'),
      },
      { ...approveStep },
      { ...enableOriginationStep },
    ],
  },
  {
    id: 'loan-product-authoring',
    title: 'طراحی محصول تسهیلات (Loan Product)',
    description:
      'مسیر واقعی طراحی یک محصول تسهیلات به سبک بانکداری BIAN: محصول → نسخه → پروفایل تسهیلات (متصل به داده مرجع بانک مرکزی) → قاعده بازپرداخت → پسوند مالی/شرایط احراز/وثیقه/فرآیند (اختیاری) → تصویب → فعال‌سازی.',
    boundedContext: 'productbuilder',
    steps: [
      {
        key: 'product',
        entity: 'Product',
        label: '۱. تعریف کلی محصول (Product)',
        help: 'نوع تراز حسابداری برای محصول تسهیلات طبق استاندارد همیشه ASSET (طلب بانک از مشتری) است و به‌صورت خودکار قفل شده.',
        fixedValues: () => ({ productClassCode: 'LOAN', balanceNatureCode: 'ASSET' }),
      },
      { ...versionStep },
      {
        key: 'profile',
        entity: 'LoanProductProfile',
        label: '۳. پروفایل تسهیلات',
        help: 'نوع تسهیلات، نوع طرح و کاربری را از داده مرجع بانک مرکزی انتخاب کنید (این‌ها از قبل seed شده‌اند).',
        fixedValues: (ctx) => ({ productVersion: { id: ctx.version.id } }),
      },
      {
        key: 'repaymentRule',
        entity: 'LoanProductRepaymentRule',
        label: '۴. قاعده بازپرداخت',
        help: 'روش بازپرداخت (اقساط مساوی/نزولی/بالون)، دوره تنفس و امکان تسویه زودهنگام.',
        fixedValues: (ctx) => ({ productVersion: { id: ctx.version.id }, recordStatusCode: 'ACTIVE' }),
        validate: (values) =>
          minMax(values, 'minTermDuration', 'maxTermDuration', 'مدت تسهیلات') ||
          minMax(values, 'minInstallmentCount', 'maxInstallmentCount', 'تعداد اقساط') ||
          minMax(values, 'minGraceDuration', 'maxGraceDuration', 'دوره تنفس') ||
          dateOrder(values, 'validFrom', 'validTo', 'بازه اعتبار قاعده بازپرداخت'),
      },
      {
        key: 'financialExtension',
        entity: 'LoanFinancialExtension',
        label: '۵. سقف مبلغ تسهیلات (اختیاری)',
        optional: true,
        fixedValues: (ctx) => ({ productVersion: { id: ctx.version.id }, ruleStatusCode: 'ACTIVE' }),
        validate: (values) =>
          minMax(values, 'minFacilityAmount', 'maxFacilityAmount', 'مبلغ تسهیلات') ||
          dateOrder(values, 'validFrom', 'validTo', 'بازه اعتبار سقف مبلغ'),
      },
      {
        key: 'eligibilityRule',
        entity: 'ProductEligibilityRule',
        label: '۶. شرایط عمومی احراز مشتری (اختیاری)',
        help: 'اگر می‌خواهید شرایط اعتباری اختصاصی تسهیلات (مرحله بعد) را هم تعریف کنید، ابتدا این مرحله را انجام دهید.',
        optional: true,
        fixedValues: (ctx) => ({ productVersion: { id: ctx.version.id }, ruleStatusCode: 'ACTIVE' }),
        validate: (values) => minMax(values, 'minAge', 'maxAge', 'سن مشتری'),
      },
      {
        key: 'loanEligibility',
        entity: 'LoanEligibilityExtension',
        label: '۷. شرایط اعتباری اختصاصی تسهیلات (اختیاری)',
        help: 'حداقل درآمد، امتیاز اعتباری و تعداد ضامن لازم - این ردیف مکمل شرایط عمومی مرحله قبل است.',
        optional: true,
        skipIf: (ctx) => !ctx.eligibilityRule,
        fixedValues: (ctx) => ({ eligibilityRule: { id: ctx.eligibilityRule.id } }),
      },
      {
        key: 'collateralRules',
        entity: 'LoanProductCollateralRule',
        label: '۸. وثیقه‌های قابل قبول (اختیاری، تکرارپذیر)',
        help: 'برای هر نوع وثیقه (ملک، سپرده، ضمانت‌نامه، ...) یک ردیف با درصد پوشش خودش تعریف کنید.',
        optional: true,
        repeatable: true,
        fixedValues: (ctx) => ({ productVersion: { id: ctx.version.id }, recordStatusCode: 'ACTIVE' }),
        validate: (values) => minMax(values, 'minCoveragePercent', 'maxCoveragePercent', 'درصد پوشش وثیقه'),
      },
      {
        key: 'processRule',
        entity: 'LoanProductProcessRule',
        label: '۹. قاعده فرآیند تصویب (اختیاری)',
        help: 'اعتبار درخواست/تصویب/قرارداد و این‌که تصویب دستی یا کمیته لازم است یا نه.',
        optional: true,
        fixedValues: (ctx) => ({ productVersion: { id: ctx.version.id }, recordStatusCode: 'ACTIVE' }),
      },
      {
        key: 'channelRules',
        entity: 'ProductChannelRule',
        label: '۱۰. کانال‌های ارائه (اختیاری، تکرارپذیر)',
        optional: true,
        repeatable: true,
        fixedValues: (ctx) => ({ productVersion: { id: ctx.version.id }, ruleStatusCode: 'ACTIVE' }),
      },
      { ...approveStep },
      { ...enableOriginationStep },
    ],
  },
];
