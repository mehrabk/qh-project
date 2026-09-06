package ir.bank.qh.productbuilder.core.enums;

/** Which product-builder bounded context a record belongs to - {@code PRODUCT_CLASS_CODE} on Product,
 * {@code MODULE_CODE} on ProductVersionModule, {@code SOURCE_DOMAIN_CODE} on ProductLegacyMapping. */
public enum ProductDomain {
    DEPOSIT,
    LOAN
}
