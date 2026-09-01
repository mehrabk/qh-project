package ir.bank.qh.deposit.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
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
public class DepositProductTermRule extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_VERSION_ID", nullable = false)
    private ProductVersion productVersion;


    @Column(name = "MIN_TERM_VALUE")
    private Integer minTermValue;


    @Column(name = "MAX_TERM_VALUE")
    private Integer maxTermValue;


    @Column(name = "TERM_UNIT_CODE", length = 20)
    private String termUnitCode;


    @Column(name = "AUTO_RENEWAL_ALLOWED", nullable = false)
    private Boolean autoRenewalAllowed = false;


    @Column(name = "RENEWAL_METHOD_CODE", length = 30)
    private String renewalMethodCode;


    @Column(name = "GRACE_PERIOD_DAYS")
    private Integer gracePeriodDays;


    @Column(name = "RULE_STATUS_CODE", nullable = false, length = 20)
    private String ruleStatusCode = "ACTIVE";
}
