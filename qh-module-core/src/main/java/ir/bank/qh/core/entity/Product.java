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
    @Column(name = "id")
    private Long id;

    @Column(name = "is_deleted", nullable = false)
    private Boolean deleted = Boolean.FALSE;


    @Column(name = "product_code", nullable = false, unique = true, length = 30)
    private String productCode;


    @Column(name = "product_name", nullable = false, length = 200)
    private String productName;


    @Column(name = "product_class_code", nullable = false, length = 30)
    private String productClassCode;


    @Column(name = "balance_nature_code", nullable = false, length = 20)
    private String balanceNatureCode;


    @Column(name = "product_family_code", length = 30)
    private String productFamilyCode;


    @Column(name = "default_currency_code", nullable = false, length = 3)
    private String defaultCurrencyCode;


    @Column(name = "description", length = 1000)
    private String description;


    @Column(name = "product_status_code", nullable = false, length = 30)
    private String productStatusCode = "DRAFT";
}
