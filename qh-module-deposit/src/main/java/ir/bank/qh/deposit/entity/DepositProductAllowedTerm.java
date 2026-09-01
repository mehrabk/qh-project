package ir.bank.qh.deposit.entity;

import ir.bank.qh.common.entity.BaseAuditableEntity;
import ir.bank.qh.deposit.entity.DepositProductTermRule;
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
public class DepositProductAllowedTerm extends BaseAuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "term_rule_id", nullable = false)
    private DepositProductTermRule termRule;


    @Column(name = "term_value", nullable = false)
    private Integer termValue;


    @Column(name = "term_unit_code", nullable = false, length = 20)
    private String termUnitCode;


    @Column(name = "is_default", nullable = false)
    private Boolean isDefault = false;


    @Column(name = "rate_override", precision = 12, scale = 8)
    private BigDecimal rateOverride;
}
