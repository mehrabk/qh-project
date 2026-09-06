package ir.bank.qh.productbuilder.commonrules.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.common.enums.RecordStatus;
import ir.bank.qh.productbuilder.commonrules.enums.PricingMethod;
import ir.bank.qh.productbuilder.commonrules.enums.PricingPurpose;
import ir.bank.qh.productbuilder.core.entity.ProductVersion;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * Top-level pricing framework for a product version: purpose, method (FIXED/TIERED/REFERENCE), base rate.
 * Maps to table PRODUCT_PRICING_RULE.
 */
@Getter
@Setter
@Entity
@Table(schema = "PRODUCTBUILDER", name = "product_pricing_rule")
public class ProductPricingRule extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_VERSION_ID", nullable = false)
    private ProductVersion productVersion;


    @Column(name = "PRICING_PURPOSE_CODE", length = 30)
    @Enumerated(EnumType.STRING)
    private PricingPurpose pricingPurposeCode;


    @Column(name = "PRICING_METHOD_CODE", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private PricingMethod pricingMethodCode;


    @Column(name = "CURRENCY_CODE", length = 3)
    private String currencyCode;


    @Column(name = "BASE_RATE", precision = 12, scale = 8)
    private BigDecimal baseRate;


    @Column(name = "IS_TIERED_RATE", nullable = false)
    private Boolean isTieredRate = false;


    @Column(name = "VALID_FROM")
    private LocalDate validFrom;


    @Column(name = "VALID_TO")
    private LocalDate validTo;


    @Column(name = "RULE_STATUS_CODE", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private RecordStatus ruleStatusCode = RecordStatus.ACTIVE;
}
