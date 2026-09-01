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
    @Column(name = "id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_version_id", nullable = false)
    private ProductVersion productVersion;


    @Column(name = "closure_type_code", nullable = false, length = 30)
    private String closureTypeCode;


    @Column(name = "is_closure_allowed", nullable = false)
    private Boolean isClosureAllowed = true;


    @Column(name = "is_partial_closure_allowed", nullable = false)
    private Boolean isPartialClosureAllowed = false;


    @Column(name = "balance_destination_code", length = 30)
    private String balanceDestinationCode;


    @Column(name = "effective_from_date")
    private LocalDate effectiveFromDate;


    @Column(name = "effective_to_date")
    private LocalDate effectiveToDate;


    @Column(name = "status_code", nullable = false, length = 20)
    private String statusCode = "ACTIVE";


    @Column(name = "description", length = 1000)
    private String description;
}
