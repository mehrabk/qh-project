package ir.bank.qh.productbuilder.loan.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.productbuilder.commonrules.entity.ProductEligibilityRule;
import ir.bank.qh.productbuilder.loan.enums.EmploymentType;
import ir.bank.qh.productbuilder.loan.enums.RiskGrade;
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
@Table(schema = "PRODUCTBUILDER", name = "loan_eligibility_extension")
public class LoanEligibilityExtension extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ELIGIBILITY_RULE_ID", nullable = false, unique = true)
    private ProductEligibilityRule eligibilityRule;


    @Column(name = "MIN_INCOME_AMOUNT", precision = 20, scale = 2)
    private BigDecimal minIncomeAmount;


    @Column(name = "MIN_CREDIT_SCORE")
    private Integer minCreditScore;


    @Column(name = "MAX_RISK_GRADE_CODE", length = 30)
    @Enumerated(EnumType.STRING)
    private RiskGrade maxRiskGradeCode;


    @Column(name = "REQUIRED_GUARANTOR_COUNT", nullable = false)
    private Integer requiredGuarantorCount = 0;


    @Column(name = "EMPLOYMENT_TYPE_CODE", length = 30)
    @Enumerated(EnumType.STRING)
    private EmploymentType employmentTypeCode;


    @Column(name = "CBI_TRACKING_REQUIRED", nullable = false)
    private Boolean cbiTrackingRequired = false;


    @Column(name = "CREDIT_INQUIRY_REQUIRED", nullable = false)
    private Boolean creditInquiryRequired = false;


    @Column(name = "AML_CHECK_REQUIRED", nullable = false)
    private Boolean amlCheckRequired = false;
}
