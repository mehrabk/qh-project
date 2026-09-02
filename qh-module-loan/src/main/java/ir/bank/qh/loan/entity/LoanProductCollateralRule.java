package ir.bank.qh.loan.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * Which collateral pattern is required/allowed for a loan product version, with minimum coverage percent.
 * Maps to table LOAN_PRODUCT_COLLATERAL_RULE.
 */
@Getter
@Setter
@Entity
@Table(schema = "LOAN", name = "loan_product_collateral_rule")
public class LoanProductCollateralRule extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @Column(name = "PRODUCT_VERSION_ID", nullable = false)
    private Long productVersionId;

    @Column(name = "LOAN_PRODUCT_COLLATERAL_ID", nullable = false)
    private Long loanProductCollateralId;


    @Column(name = "IS_REQUIRED", nullable = false)
    private Boolean isRequired = true;


    @Column(name = "MIN_COVERAGE_PERCENT", precision = 7, scale = 4)
    private BigDecimal minCoveragePercent;


    @Column(name = "MAX_COVERAGE_PERCENT", precision = 7, scale = 4)
    private BigDecimal maxCoveragePercent;


    @Column(name = "SUBSTITUTION_ALLOWED", nullable = false)
    private Boolean substitutionAllowed = false;


    @Column(name = "THIRD_PARTY_ALLOWED", nullable = false)
    private Boolean thirdPartyAllowed = true;


    @Column(name = "CURRENCY_MATCH_REQUIRED", nullable = false)
    private Boolean currencyMatchRequired = true;


    @Column(name = "PRIORITY_NO", nullable = false)
    private Integer priorityNo = 1;


    @Column(name = "RECORD_STATUS_CODE", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
