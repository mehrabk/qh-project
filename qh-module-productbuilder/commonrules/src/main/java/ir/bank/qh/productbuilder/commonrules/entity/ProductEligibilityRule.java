package ir.bank.qh.productbuilder.commonrules.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.common.enums.PartyNature;
import ir.bank.qh.common.enums.RecordStatus;
import ir.bank.qh.productbuilder.commonrules.enums.CustomerSegment;
import ir.bank.qh.productbuilder.commonrules.enums.Gender;
import ir.bank.qh.productbuilder.commonrules.enums.KycLevel;
import ir.bank.qh.productbuilder.commonrules.enums.NationalityScope;
import ir.bank.qh.productbuilder.commonrules.enums.ResidencyStatus;
import ir.bank.qh.productbuilder.commonrules.enums.RiskLevel;
import ir.bank.qh.productbuilder.core.entity.ProductVersion;
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
@Table(schema = "PRODUCTBUILDER", name = "product_eligibility_rule")
public class ProductEligibilityRule extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_VERSION_ID", nullable = false)
    private ProductVersion productVersion;


    @Column(name = "PARTY_TYPE_CODE", length = 30)
    @Enumerated(EnumType.STRING)
    private PartyNature partyTypeCode;


    @Column(name = "CUSTOMER_SEGMENT_CODE", length = 30)
    @Enumerated(EnumType.STRING)
    private CustomerSegment customerSegmentCode;


    @Column(name = "RESIDENCY_STATUS_CODE", length = 30)
    @Enumerated(EnumType.STRING)
    private ResidencyStatus residencyStatusCode;


    @Column(name = "NATIONALITY_SCOPE_CODE", length = 30)
    @Enumerated(EnumType.STRING)
    private NationalityScope nationalityScopeCode;


    @Column(name = "GENDER_CODE", length = 30)
    @Enumerated(EnumType.STRING)
    private Gender genderCode;


    @Column(name = "KYC_LEVEL_CODE", length = 30)
    @Enumerated(EnumType.STRING)
    private KycLevel kycLevelCode;


    @Column(name = "MIN_AGE")
    private Integer minAge;


    @Column(name = "MAX_AGE")
    private Integer maxAge;


    @Column(name = "AML_RISK_MAX_CODE", length = 30)
    @Enumerated(EnumType.STRING)
    private RiskLevel amlRiskMaxCode;


    @Column(name = "PEP_ALLOWED_FLAG", nullable = false)
    private Boolean pepAllowedFlag = false;


    @Column(name = "MIN_CUSTOMER_TENURE_MONTHS")
    private Integer minCustomerTenureMonths;


    @Column(name = "MIN_ACCOUNT_TENURE_MONTHS")
    private Integer minAccountTenureMonths;


    @Column(name = "MIN_AVERAGE_BALANCE", precision = 20, scale = 4)
    private BigDecimal minAverageBalance;


    @Column(name = "PRIORITY_NO", nullable = false)
    private Integer priorityNo = 1;


    @Column(name = "RULE_STATUS_CODE", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private RecordStatus ruleStatusCode = RecordStatus.ACTIVE;
}
