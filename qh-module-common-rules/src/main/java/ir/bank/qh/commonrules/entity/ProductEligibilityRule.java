package ir.bank.qh.commonrules.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.core.entity.ProductVersion;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * Who may open/hold this product version: party type, segment, age range, KYC level, AML risk ceiling, PEP flag.
 * Maps to table PRODUCT_ELIGIBILITY_RULE.
 */
@Getter
@Setter
@Entity
@Table(schema = "COMMONRULES", name = "product_eligibility_rule")
public class ProductEligibilityRule extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_version_id", nullable = false)
    private ProductVersion productVersion;


    @Column(name = "party_type_code", length = 30)
    private String partyTypeCode;


    @Column(name = "customer_segment_code", length = 30)
    private String customerSegmentCode;


    @Column(name = "residency_status_code", length = 30)
    private String residencyStatusCode;


    @Column(name = "nationality_scope_code", length = 30)
    private String nationalityScopeCode;


    @Column(name = "gender_code", length = 30)
    private String genderCode;


    @Column(name = "kyc_level_code", length = 30)
    private String kycLevelCode;


    @Column(name = "min_age")
    private Integer minAge;


    @Column(name = "max_age")
    private Integer maxAge;


    @Column(name = "aml_risk_max_code", length = 30)
    private String amlRiskMaxCode;


    @Column(name = "pep_allowed_flag", nullable = false)
    private Boolean pepAllowedFlag = false;


    @Column(name = "min_customer_tenure_months")
    private Integer minCustomerTenureMonths;


    @Column(name = "min_account_tenure_months")
    private Integer minAccountTenureMonths;


    @Column(name = "min_average_balance", precision = 20, scale = 4)
    private BigDecimal minAverageBalance;


    @Column(name = "priority_no", nullable = false)
    private Integer priorityNo = 1;


    @Column(name = "rule_status_code", nullable = false, length = 20)
    private String ruleStatusCode = "ACTIVE";
}
