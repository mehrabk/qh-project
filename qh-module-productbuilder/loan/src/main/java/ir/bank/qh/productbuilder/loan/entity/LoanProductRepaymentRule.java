package ir.bank.qh.productbuilder.loan.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.common.enums.RecordStatus;
import ir.bank.qh.common.enums.TermUnit;
import ir.bank.qh.productbuilder.core.entity.ProductVersion;
import ir.bank.qh.productbuilder.loan.enums.GraceMethod;
import ir.bank.qh.productbuilder.loan.enums.InstallmentFrequency;
import ir.bank.qh.productbuilder.loan.enums.RepaymentMethod;
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
@Table(schema = "PRODUCTBUILDER", name = "loan_product_repayment_rule")
public class LoanProductRepaymentRule extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_VERSION_ID", nullable = false)
    private ProductVersion productVersion;


    @Column(name = "REPAYMENT_RULE_CODE", nullable = false, length = 30)
    private String repaymentRuleCode;


    @Column(name = "REPAYMENT_RULE_NAME", length = 200)
    private String repaymentRuleName;


    @Column(name = "REPAYMENT_METHOD_CODE", length = 30)
    @Enumerated(EnumType.STRING)
    private RepaymentMethod repaymentMethodCode;


    @Column(name = "INSTALLMENT_FREQUENCY_CODE", length = 30)
    @Enumerated(EnumType.STRING)
    private InstallmentFrequency installmentFrequencyCode;


    @Column(name = "MIN_TERM_DURATION")
    private Integer minTermDuration;


    @Column(name = "MAX_TERM_DURATION")
    private Integer maxTermDuration;


    @Column(name = "TERM_UNIT_CODE", length = 20)
    @Enumerated(EnumType.STRING)
    private TermUnit termUnitCode;


    @Column(name = "MIN_INSTALLMENT_COUNT")
    private Integer minInstallmentCount;


    @Column(name = "MAX_INSTALLMENT_COUNT")
    private Integer maxInstallmentCount;


    @Column(name = "INSTALLMENT_INTERVAL")
    private Integer installmentInterval;


    @Column(name = "INSTALLMENT_INTERVAL_UNIT_CODE", length = 30)
    @Enumerated(EnumType.STRING)
    private TermUnit installmentIntervalUnitCode;


    @Column(name = "GRACE_ALLOWED", nullable = false)
    private Boolean graceAllowed = false;


    @Column(name = "MIN_GRACE_DURATION")
    private Integer minGraceDuration;


    @Column(name = "MAX_GRACE_DURATION")
    private Integer maxGraceDuration;


    @Column(name = "GRACE_UNIT_CODE", length = 30)
    @Enumerated(EnumType.STRING)
    private TermUnit graceUnitCode;


    @Column(name = "GRACE_METHOD_CODE", length = 30)
    @Enumerated(EnumType.STRING)
    private GraceMethod graceMethodCode;


    @Column(name = "PREPAYMENT_ALLOWED", nullable = false)
    private Boolean prepaymentAllowed = false;


    @Column(name = "PARTIAL_PREPAYMENT_ALLOWED", nullable = false)
    private Boolean partialPrepaymentAllowed = false;


    @Column(name = "EARLY_SETTLEMENT_ALLOWED", nullable = false)
    private Boolean earlySettlementAllowed = true;


    @Column(name = "VALID_FROM")
    private LocalDate validFrom;


    @Column(name = "VALID_TO")
    private LocalDate validTo;


    @Column(name = "RECORD_STATUS_CODE", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private RecordStatus recordStatusCode = RecordStatus.ACTIVE;
}
