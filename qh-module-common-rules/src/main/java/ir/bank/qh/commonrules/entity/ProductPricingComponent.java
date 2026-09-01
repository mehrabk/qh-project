package ir.bank.qh.commonrules.entity;

import ir.bank.qh.common.entity.BaseAuditableEntity;
import ir.bank.qh.commonrules.entity.ProductPricingRule;
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
public class ProductPricingComponent extends BaseAuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pricing_rule_id", nullable = false)
    private ProductPricingRule pricingRule;


    @Column(name = "component_type_code", nullable = false, length = 30)
    private String componentTypeCode;


    @Column(name = "calculation_method_code", length = 30)
    private String calculationMethodCode;


    @Column(name = "rate_value", precision = 12, scale = 8)
    private BigDecimal rateValue;


    @Column(name = "min_rate", precision = 12, scale = 8)
    private BigDecimal minRate;


    @Column(name = "max_rate", precision = 12, scale = 8)
    private BigDecimal maxRate;


    @Column(name = "fixed_amount", precision = 20, scale = 4)
    private BigDecimal fixedAmount;


    @Column(name = "min_amount", precision = 20, scale = 4)
    private BigDecimal minAmount;


    @Column(name = "max_amount", precision = 20, scale = 4)
    private BigDecimal maxAmount;


    @Column(name = "percentage_base_code", length = 30)
    private String percentageBaseCode;


    @Column(name = "is_waivable", nullable = false)
    private Boolean isWaivable = false;


    @Column(name = "display_order", nullable = false)
    private Integer displayOrder = 1;


    @Column(name = "record_status_code", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
