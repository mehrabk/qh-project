package ir.bank.qh.core.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Stable product identity (PRODUCT). Does not change across versions.
 * Maps to table PRODUCT.
 */
@Getter
@Setter
@Entity
@Table(schema = "CORE", name = "product")
public class Product extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "IS_DELETED", nullable = false)
    private Boolean deleted = Boolean.FALSE;


    @Column(name = "PRODUCT_CODE", nullable = false, unique = true, length = 30)
    private String productCode;


    @Column(name = "PRODUCT_NAME", nullable = false, length = 200)
    private String productName;


    @Column(name = "PRODUCT_CLASS_CODE", nullable = false, length = 30)
    private String productClassCode;


    @Column(name = "BALANCE_NATURE_CODE", nullable = false, length = 20)
    private String balanceNatureCode;


    @Column(name = "PRODUCT_FAMILY_CODE", length = 30)
    private String productFamilyCode;


    @Column(name = "DEFAULT_CURRENCY_CODE", nullable = false, length = 3)
    private String defaultCurrencyCode;


    @Column(name = "DESCRIPTION", length = 1000)
    private String description;


    @Column(name = "PRODUCT_STATUS_CODE", nullable = false, length = 30)
    private String productStatusCode = "DRAFT";
}
