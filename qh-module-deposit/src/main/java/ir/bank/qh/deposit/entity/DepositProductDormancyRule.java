package ir.bank.qh.deposit.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
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
public class DepositProductDormancyRule extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @Column(name = "PRODUCT_VERSION_ID", nullable = false)
    private Long productVersionId;


    @Column(name = "INACTIVITY_PERIOD_VALUE")
    private Integer inactivityPeriodValue;


    @Column(name = "INACTIVITY_PERIOD_UNIT_CODE", length = 30)
    private String inactivityPeriodUnitCode;


    @Column(name = "WARNING_PERIOD_VALUE")
    private Integer warningPeriodValue;


    @Column(name = "WARNING_PERIOD_UNIT_CODE", length = 30)
    private String warningPeriodUnitCode;


    @Column(name = "REACTIVATION_METHOD_CODE", length = 30)
    private String reactivationMethodCode;


    @Column(name = "AUTO_REACTIVATION_ALLOWED", nullable = false)
    private Boolean autoReactivationAllowed = false;


    @Column(name = "VALID_FROM")
    private LocalDate validFrom;


    @Column(name = "VALID_TO")
    private LocalDate validTo;
}
