package ir.bank.qh.deposit.entity;

import ir.bank.qh.common.entity.BaseAuditableEntity;
import ir.bank.qh.core.entity.ProductVersion;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * Inactivity-to-dormant transition rule, warning period, and reactivation method.
 * Maps to table DEPOSIT_PRODUCT_DORMANCY_RULE.
 */
@Getter
@Setter
@Entity
@Table(schema = "DEPOSIT", name = "deposit_product_dormancy_rule")
public class DepositProductDormancyRule extends BaseAuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_version_id", nullable = false)
    private ProductVersion productVersion;


    @Column(name = "inactivity_period_value")
    private Integer inactivityPeriodValue;


    @Column(name = "inactivity_period_unit_code", length = 30)
    private String inactivityPeriodUnitCode;


    @Column(name = "warning_period_value")
    private Integer warningPeriodValue;


    @Column(name = "warning_period_unit_code", length = 30)
    private String warningPeriodUnitCode;


    @Column(name = "reactivation_method_code", length = 30)
    private String reactivationMethodCode;


    @Column(name = "auto_reactivation_allowed", nullable = false)
    private Boolean autoReactivationAllowed = false;


    @Column(name = "valid_from")
    private LocalDate validFrom;


    @Column(name = "valid_to")
    private LocalDate validTo;
}
