package ir.bank.qh.productbuilder.loan.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.productbuilder.core.entity.ProductVersion;
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
@Table(schema = "PRODUCTBUILDER", name = "loan_financial_extension")
public class LoanFinancialExtension extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_VERSION_ID", nullable = false, unique = true)
    private ProductVersion productVersion;


    @Column(name = "MIN_FACILITY_AMOUNT", precision = 20, scale = 2)
    private BigDecimal minFacilityAmount;


    @Column(name = "MAX_FACILITY_AMOUNT", precision = 20, scale = 2)
    private BigDecimal maxFacilityAmount;


    @Column(name = "VALID_FROM")
    private LocalDate validFrom;


    @Column(name = "VALID_TO")
    private LocalDate validTo;


    @Column(name = "RULE_STATUS_CODE", nullable = false, length = 20)
    private String ruleStatusCode = "ACTIVE";
}
