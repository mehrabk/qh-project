package ir.bank.qh.deposit.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * The concrete list of allowed terms for a DEPOSIT_PRODUCT_TERM_RULE, with an optional per-term rate override.
 * Maps to table DEPOSIT_PRODUCT_ALLOWED_TERM.
 */
@Getter
@Setter
@Entity
@Table(schema = "DEPOSIT", name = "deposit_product_allowed_term")
public class DepositProductAllowedTerm extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TERM_RULE_ID", nullable = false)
    private DepositProductTermRule termRule;


    @Column(name = "TERM_VALUE", nullable = false)
    private Integer termValue;


    @Column(name = "TERM_UNIT_CODE", nullable = false, length = 20)
    private String termUnitCode;


    @Column(name = "IS_DEFAULT", nullable = false)
    private Boolean isDefault = false;


    @Column(name = "RATE_OVERRIDE", precision = 12, scale = 8)
    private BigDecimal rateOverride;
}
