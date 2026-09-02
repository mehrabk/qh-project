package ir.bank.qh.deposit.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.core.entity.ProductVersion;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * Top-level closure policy: allowed/partial closure, balance destination, effective date range.
 * Maps to table DEPOSIT_PRODUCT_CLOSURE_RULE.
 */
@Getter
@Setter
@Entity
@Table(schema = "DEPOSIT", name = "deposit_product_closure_rule")
public class DepositProductClosureRule extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_VERSION_ID", nullable = false)
    private ProductVersion productVersion;


    @Column(name = "CLOSURE_TYPE_CODE", nullable = false, length = 30)
    private String closureTypeCode;


    @Column(name = "IS_CLOSURE_ALLOWED", nullable = false)
    private Boolean isClosureAllowed = true;


    @Column(name = "IS_PARTIAL_CLOSURE_ALLOWED", nullable = false)
    private Boolean isPartialClosureAllowed = false;


    @Column(name = "BALANCE_DESTINATION_CODE", length = 30)
    private String balanceDestinationCode;


    @Column(name = "EFFECTIVE_FROM_DATE")
    private LocalDate effectiveFromDate;


    @Column(name = "EFFECTIVE_TO_DATE")
    private LocalDate effectiveToDate;


    @Column(name = "STATUS_CODE", nullable = false, length = 20)
    private String statusCode = "ACTIVE";


    @Column(name = "DESCRIPTION", length = 1000)
    private String description;
}
