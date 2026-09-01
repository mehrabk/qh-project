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
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRICING_COMPONENT_ID", nullable = false)
    private ProductPricingComponent pricingComponent;


    @Column(name = "TIER_NO", nullable = false)
    private Integer tierNo;


    @Column(name = "MIN_AMOUNT", precision = 20, scale = 4)
    private BigDecimal minAmount;


    @Column(name = "MAX_AMOUNT", precision = 20, scale = 4)
    private BigDecimal maxAmount;


    @Column(name = "MIN_TERM_VALUE")
    private Integer minTermValue;


    @Column(name = "MAX_TERM_VALUE")
    private Integer maxTermValue;


    @Column(name = "TERM_UNIT_CODE", length = 20)
    private String termUnitCode;


    @Column(name = "RATE_VALUE", precision = 12, scale = 8)
    private BigDecimal rateValue;


    @Column(name = "VALID_FROM")
    private LocalDate validFrom;


    @Column(name = "VALID_TO")
    private LocalDate validTo;
}
