package ir.bank.qh.loan.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.core.entity.ProductVersion;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * Repayment method, installment frequency/count bounds, grace period policy, prepayment/early-settlement flags.
 * Maps to table LOAN_PRODUCT_REPAYMENT_RULE.
 */
@Getter
@Setter
@Entity
@Table(schema = "LOAN", name = "loan_product_repayment_rule")
public class LoanProductRepaymentRule extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_version_id", nullable = false)
    private ProductVersion productVersion;


    @Column(name = "repayment_rule_code", nullable = false, length = 30)
    private String repaymentRuleCode;


    @Column(name = "repayment_rule_name", length = 200)
    private String repaymentRuleName;


    @Column(name = "repayment_method_code", length = 30)
    private String repaymentMethodCode;


    @Column(name = "installment_frequency_code", length = 30)
    private String installmentFrequencyCode;


    @Column(name = "min_term_duration")
    private Integer minTermDuration;


    @Column(name = "max_term_duration")
    private Integer maxTermDuration;


    @Column(name = "term_unit_code", length = 20)
    private String termUnitCode;


    @Column(name = "min_installment_count")
    private Integer minInstallmentCount;


    @Column(name = "max_installment_count")
    private Integer maxInstallmentCount;


    @Column(name = "installment_interval")
    private Integer installmentInterval;


    @Column(name = "installment_interval_unit_code", length = 30)
    private String installmentIntervalUnitCode;


    @Column(name = "grace_allowed", nullable = false)
    private Boolean graceAllowed = false;


    @Column(name = "min_grace_duration")
    private Integer minGraceDuration;


    @Column(name = "max_grace_duration")
    private Integer maxGraceDuration;


    @Column(name = "grace_unit_code", length = 30)
    private String graceUnitCode;


    @Column(name = "grace_method_code", length = 30)
    private String graceMethodCode;


    @Column(name = "prepayment_allowed", nullable = false)
    private Boolean prepaymentAllowed = false;


    @Column(name = "partial_prepayment_allowed", nullable = false)
    private Boolean partialPrepaymentAllowed = false;


    @Column(name = "early_settlement_allowed", nullable = false)
    private Boolean earlySettlementAllowed = true;


    @Column(name = "valid_from")
    private LocalDate validFrom;


    @Column(name = "valid_to")
    private LocalDate validTo;


    @Column(name = "record_status_code", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
