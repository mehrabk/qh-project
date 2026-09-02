package ir.bank.qh.commonrules.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * A financial component (fee, charge, waivable bonus...) belonging to a pricing rule.
 * Maps to table PRODUCT_PRICING_COMPONENT.
 */
@Getter
@Setter
@Entity
@Table(schema = "COMMONRULES", name = "product_pricing_component")
public class ProductPricingComponent extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRICING_RULE_ID", nullable = false)
    private ProductPricingRule pricingRule;


    @Column(name = "COMPONENT_TYPE_CODE", nullable = false, length = 30)
    private String componentTypeCode;


    @Column(name = "CALCULATION_METHOD_CODE", length = 30)
    private String calculationMethodCode;


    @Column(name = "RATE_VALUE", precision = 12, scale = 8)
    private BigDecimal rateValue;


    @Column(name = "MIN_RATE", precision = 12, scale = 8)
    private BigDecimal minRate;


    @Column(name = "MAX_RATE", precision = 12, scale = 8)
    private BigDecimal maxRate;


    @Column(name = "FIXED_AMOUNT", precision = 20, scale = 4)
    private BigDecimal fixedAmount;


    @Column(name = "MIN_AMOUNT", precision = 20, scale = 4)
    private BigDecimal minAmount;


    @Column(name = "MAX_AMOUNT", precision = 20, scale = 4)
    private BigDecimal maxAmount;


    @Column(name = "PERCENTAGE_BASE_CODE", length = 30)
    private String percentageBaseCode;


    @Column(name = "IS_WAIVABLE", nullable = false)
    private Boolean isWaivable = false;


    @Column(name = "DISPLAY_ORDER", nullable = false)
    private Integer displayOrder = 1;


    @Column(name = "RECORD_STATUS_CODE", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
