package ir.bank.qh.loan.entity;

import ir.bank.qh.common.entity.BaseAuditableEntity;
import ir.bank.qh.core.entity.ProductVersion;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * Facility amount boundaries (min/max) for a loan product version.
 * Maps to table LOAN_FINANCIAL_EXTENSION.
 */
@Getter
@Setter
@Entity
@Table(schema = "LOAN", name = "loan_financial_extension")
public class LoanFinancialExtension extends BaseAuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_version_id", nullable = false, unique = true)
    private ProductVersion productVersion;


    @Column(name = "min_facility_amount", precision = 20, scale = 2)
    private BigDecimal minFacilityAmount;


    @Column(name = "max_facility_amount", precision = 20, scale = 2)
    private BigDecimal maxFacilityAmount;


    @Column(name = "valid_from")
    private LocalDate validFrom;


    @Column(name = "valid_to")
    private LocalDate validTo;


    @Column(name = "rule_status_code", nullable = false, length = 20)
    private String ruleStatusCode = "ACTIVE";
}
