package ir.bank.qh.loan.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.core.entity.ProductVersion;
import ir.bank.qh.reference.entity.LoanProductCollateral;
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
    @Column(name = "id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_version_id", nullable = false)
    private ProductVersion productVersion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_product_collateral_id", nullable = false)
    private LoanProductCollateral loanProductCollateral;


    @Column(name = "is_required", nullable = false)
    private Boolean isRequired = true;


    @Column(name = "min_coverage_percent", precision = 7, scale = 4)
    private BigDecimal minCoveragePercent;


    @Column(name = "max_coverage_percent", precision = 7, scale = 4)
    private BigDecimal maxCoveragePercent;


    @Column(name = "substitution_allowed", nullable = false)
    private Boolean substitutionAllowed = false;


    @Column(name = "third_party_allowed", nullable = false)
    private Boolean thirdPartyAllowed = true;


    @Column(name = "currency_match_required", nullable = false)
    private Boolean currencyMatchRequired = true;


    @Column(name = "priority_no", nullable = false)
    private Integer priorityNo = 1;


    @Column(name = "record_status_code", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
