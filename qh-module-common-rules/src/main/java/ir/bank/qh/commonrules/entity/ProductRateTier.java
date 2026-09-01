package ir.bank.qh.commonrules.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.commonrules.entity.ProductPricingComponent;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * A tiered rate step (amount/term range -> rate) belonging to a pricing component.
 * Maps to table PRODUCT_RATE_TIER.
 */
@Getter
@Setter
@Entity
@Table(schema = "COMMONRULES", name = "product_rate_tier")
public class ProductRateTier extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pricing_component_id", nullable = false)
    private ProductPricingComponent pricingComponent;


    @Column(name = "tier_no", nullable = false)
    private Integer tierNo;


    @Column(name = "min_amount", precision = 20, scale = 4)
    private BigDecimal minAmount;


    @Column(name = "max_amount", precision = 20, scale = 4)
    private BigDecimal maxAmount;


    @Column(name = "min_term_value")
    private Integer minTermValue;


    @Column(name = "max_term_value")
    private Integer maxTermValue;


    @Column(name = "term_unit_code", length = 20)
    private String termUnitCode;


    @Column(name = "rate_value", precision = 12, scale = 8)
    private BigDecimal rateValue;


    @Column(name = "valid_from")
    private LocalDate validFrom;


    @Column(name = "valid_to")
    private LocalDate validTo;
}
