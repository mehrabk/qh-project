package ir.bank.qh.commonrules.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.core.entity.ProductVersion;
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
@Table(schema = "COMMONRULES", name = "product_pricing_rule")
public class ProductPricingRule extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_version_id", nullable = false)
    private ProductVersion productVersion;


    @Column(name = "pricing_purpose_code", length = 30)
    private String pricingPurposeCode;


    @Column(name = "pricing_method_code", nullable = false, length = 30)
    private String pricingMethodCode;


    @Column(name = "currency_code", length = 3)
    private String currencyCode;


    @Column(name = "base_rate", precision = 12, scale = 8)
    private BigDecimal baseRate;


    @Column(name = "is_tiered_rate", nullable = false)
    private Boolean isTieredRate = false;


    @Column(name = "valid_from")
    private LocalDate validFrom;


    @Column(name = "valid_to")
    private LocalDate validTo;


    @Column(name = "rule_status_code", nullable = false, length = 20)
    private String ruleStatusCode = "ACTIVE";
}
