// Guided, multi-step data-entry wizards for the Party bounded context.
//
// Unlike scenarios.js (which auto-fills every field with placeholder data to
// smoke-test the API chain), a wizard renders a REAL editable form per step -
// the user types the actual values - while automatically carrying forward
// FK links a previous step already established (e.g. once a Party is
// created, every later step's `party`/`partyMembership` field is pre-filled
// and locked instead of asked again).
//
// Step shape:
//   key          - unique id within the wizard; the created/selected record
//                  is stored under ctx[key] for later steps' fixedValues().
//   entity       - entity name as it appears in entitiesMeta.json.
//   label        - Persian step title.
//   help         - optional short Persian explanation shown under the title.
//   fixedValues  - optional (ctx) => partial payload. Every field named here
//                  is removed from the editable form and shown instead as a
//                  read-only "auto-linked" line.
//   optional     - if true, a "رد کردن این مرحله" button skips the step
//                  without creating anything; ctx[key] stays undefined.
//   skipIf       - optional (ctx) => bool; step is hidden/auto-skipped
//                  entirely (e.g. "link the address" when no address was
//                  created in the previous, optional step).
//   repeatable   - if true, after a successful create the user can choose
//                  "افزودن مورد دیگر" to submit another row of the same step
//                  instead of advancing; ctx[key] becomes an array.

export const wizards = [
  {
    id: 'person-onboarding',
    title: 'ثبت شخص حقیقی (Person Onboarding)',
    description:
      'مسیر کامل ثبت یک شخص حقیقی: ایجاد هسته هویتی، مشخصات فردی، عضویت در صندوق، نشانی، تماس، شناسه هویتی و مشتری‌شدن - هر مرحله را با مقادیر واقعی خودتان پر می‌کنید.',
    boundedContext: 'party',
    steps: [
      {
        key: 'party',
        entity: 'PartyEntity',
        label: '۱. ایجاد هسته هویتی (Party)',
        fixedValues: () => ({ partyType: { id: 'PERSON' } }),
      },
      {
        key: 'person',
        entity: 'PersonEntity',
        label: '۲. مشخصات فردی (Person)',
        fixedValues: (ctx) => ({ party: { id: ctx.party.id } }),
      },
      {
        key: 'membership',
        entity: 'PartyMembershipEntity',
        label: '۳. عضویت در صندوق (Party Membership)',
        help: 'مرز اصلی داده صندوق - آدرس، تماس، نقش و مشتری‌بودن همگی به این عضویت متصل می‌شوند.',
        fixedValues: (ctx) => ({ party: { id: ctx.party.id } }),
      },
      {
        key: 'address',
        entity: 'AddressEntity',
        label: '۴. نشانی (اختیاری)',
        optional: true,
      },
      {
        key: 'partyAddress',
        entity: 'PartyAddressEntity',
        label: '۵. اتصال نشانی به عضویت',
        skipIf: (ctx) => !ctx.address,
        fixedValues: (ctx) => ({
          partyMembership: { id: ctx.membership.id },
          address: { id: ctx.address.id },
        }),
      },
      {
        key: 'contactPoint',
        entity: 'ContactPointEntity',
        label: '۶. راه ارتباطی (اختیاری)',
        optional: true,
        fixedValues: (ctx) => ({ partyMembership: { id: ctx.membership.id } }),
      },
      {
        key: 'identifier',
        entity: 'PartyIdentifierEntity',
        label: '۷. شناسه هویتی مانند کد ملی (اختیاری)',
        optional: true,
        fixedValues: (ctx) => ({ party: { id: ctx.party.id } }),
      },
      {
        key: 'customer',
        entity: 'CustomerEntity',
        label: '۸. مشتری‌کردن این شخص در صندوق (اختیاری)',
        optional: true,
        fixedValues: (ctx) => ({ partyMembership: { id: ctx.membership.id } }),
      },
    ],
  },
  {
    id: 'organization-onboarding',
    title: 'ثبت شخص حقوقی (Organization Onboarding)',
    description:
      'مسیر کامل ثبت یک شخص حقوقی: ایجاد هسته هویتی، مشخصات شرکت، عضویت در صندوق، نشانی، تماس، افسر/نماینده (از میان اشخاص حقیقی موجود) و مشتری‌شدن.',
    boundedContext: 'party',
    steps: [
      {
        key: 'party',
        entity: 'PartyEntity',
        label: '۱. ایجاد هسته هویتی (Party)',
        fixedValues: () => ({ partyType: { id: 'ORGANIZATION' } }),
      },
      {
        key: 'organization',
        entity: 'OrganizationEntity',
        label: '۲. مشخصات شرکت (Organization)',
        fixedValues: (ctx) => ({ party: { id: ctx.party.id } }),
      },
      {
        key: 'membership',
        entity: 'PartyMembershipEntity',
        label: '۳. عضویت در صندوق (Party Membership)',
        fixedValues: (ctx) => ({ party: { id: ctx.party.id } }),
      },
      {
        key: 'address',
        entity: 'AddressEntity',
        label: '۴. نشانی ثبتی (اختیاری)',
        optional: true,
      },
      {
        key: 'partyAddress',
        entity: 'PartyAddressEntity',
        label: '۵. اتصال نشانی به عضویت',
        skipIf: (ctx) => !ctx.address,
        fixedValues: (ctx) => ({
          partyMembership: { id: ctx.membership.id },
          address: { id: ctx.address.id },
        }),
      },
      {
        key: 'contactPoint',
        entity: 'ContactPointEntity',
        label: '۶. راه ارتباطی (اختیاری)',
        optional: true,
        fixedValues: (ctx) => ({ partyMembership: { id: ctx.membership.id } }),
      },
      {
        key: 'officer',
        entity: 'OrganizationOfficerEntity',
        label: '۷. تعیین افسر/نماینده از میان اشخاص حقیقی موجود (اختیاری)',
        help: 'شخص حقیقی افسر باید از قبل در سامانه ثبت و در یک صندوق عضو شده باشد.',
        optional: true,
        fixedValues: (ctx) => ({ organizationMembership: { id: ctx.membership.id } }),
      },
      {
        key: 'customer',
        entity: 'CustomerEntity',
        label: '۸. مشتری‌کردن این شرکت در صندوق (اختیاری)',
        optional: true,
        fixedValues: (ctx) => ({ partyMembership: { id: ctx.membership.id } }),
      },
    ],
  },
  {
    id: 'assign-role',
    title: 'تخصیص نقش به یک عضویت موجود (Party Role)',
    description:
      'یک نقش جدید (مشتری، وکیل، ضامن، نماینده قانونی و ...) برای یک عضویت صندوقی که از قبل وجود دارد ثبت می‌کند.',
    boundedContext: 'party',
    steps: [
      {
        key: 'role',
        entity: 'PartyRoleEntity',
        label: '۱. تعیین عضویت و نقش',
        help: 'عضویت (Party Membership) هدف را از فهرست انتخاب کنید.',
      },
    ],
  },
  {
    id: 'ownership-group',
    title: 'ثبت گروه مالکیت/ذی‌نفع واحد (UBO Group)',
    description:
      'یک گروه مالکیت جدید می‌سازد و امکان افزودن چند عضو (هرکدام یک عضویت صندوقی موجود، با درصد مالکیت) را به همان گروه فراهم می‌کند.',
    boundedContext: 'party',
    steps: [
      {
        key: 'group',
        entity: 'PartyGroupEntity',
        label: '۱. ایجاد گروه',
      },
      {
        key: 'members',
        entity: 'PartyGroupMemberEntity',
        label: '۲. افزودن اعضای گروه',
        help: 'برای هر عضو یک عضویت صندوقی موجود را انتخاب و درصد مالکیت را وارد کنید.',
        repeatable: true,
        fixedValues: (ctx) => ({ group: { id: ctx.group.id } }),
      },
    ],
  },
];
