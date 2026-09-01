package ir.bank.qh.deposit.entity;

import ir.bank.qh.common.entity.BaseAuditableEntity;
import ir.bank.qh.core.entity.ProductVersion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Term/maturity behaviour for term deposits (not applicable to QH Savings). Auto-renewal and grace policy.
 * Maps to table DEPOSIT_PRODUCT_TERM_RULE.
 */
@Getter
@Setter
@Entity
@Table(schema = "DEPOSIT", name = "deposit_product_term_rule")
public class DepositProductTermRule extends BaseAuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_version_id", nullable = false)
    private ProductVersion productVersion;


    @Column(name = "min_term_value")
    private Integer minTermValue;


    @Column(name = "max_term_value")
    private Integer maxTermValue;


    @Column(name = "term_unit_code", length = 20)
    private String termUnitCode;


    @Column(name = "auto_renewal_allowed", nullable = false)
    private Boolean autoRenewalAllowed = false;


    @Column(name = "renewal_method_code", length = 30)
    private String renewalMethodCode;


    @Column(name = "grace_period_days")
    private Integer gracePeriodDays;


    @Column(name = "rule_status_code", nullable = false, length = 20)
    private String ruleStatusCode = "ACTIVE";
}
