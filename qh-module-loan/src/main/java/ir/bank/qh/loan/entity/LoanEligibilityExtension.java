package ir.bank.qh.loan.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.commonrules.entity.ProductEligibilityRule;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * Loan-specific extension of the common PRODUCT_ELIGIBILITY_RULE: credit score, guarantor count, KYC/AML flags.
 * Maps to table LOAN_ELIGIBILITY_EXTENSION.
 */
@Getter
@Setter
@Entity
@Table(schema = "LOAN", name = "loan_eligibility_extension")
public class LoanEligibilityExtension extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eligibility_rule_id", nullable = false, unique = true)
    private ProductEligibilityRule eligibilityRule;


    @Column(name = "min_income_amount", precision = 20, scale = 2)
    private BigDecimal minIncomeAmount;


    @Column(name = "min_credit_score")
    private Integer minCreditScore;


    @Column(name = "max_risk_grade_code", length = 30)
    private String maxRiskGradeCode;


    @Column(name = "required_guarantor_count", nullable = false)
    private Integer requiredGuarantorCount = 0;


    @Column(name = "employment_type_code", length = 30)
    private String employmentTypeCode;


    @Column(name = "cbi_tracking_required", nullable = false)
    private Boolean cbiTrackingRequired = false;


    @Column(name = "credit_inquiry_required", nullable = false)
    private Boolean creditInquiryRequired = false;


    @Column(name = "aml_check_required", nullable = false)
    private Boolean amlCheckRequired = false;
}
