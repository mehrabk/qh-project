package ir.bank.qh.productbuilder.deposit.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.common.enums.TermUnit;
import ir.bank.qh.productbuilder.core.entity.ProductVersion;
import ir.bank.qh.productbuilder.deposit.enums.ReactivationMethod;
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
@Table(schema = "PRODUCTBUILDER", name = "deposit_product_dormancy_rule")
public class DepositProductDormancyRule extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_VERSION_ID", nullable = false)
    private ProductVersion productVersion;


    @Column(name = "INACTIVITY_PERIOD_VALUE")
    private Integer inactivityPeriodValue;


    @Column(name = "INACTIVITY_PERIOD_UNIT_CODE", length = 30)
    @Enumerated(EnumType.STRING)
    private TermUnit inactivityPeriodUnitCode;


    @Column(name = "WARNING_PERIOD_VALUE")
    private Integer warningPeriodValue;


    @Column(name = "WARNING_PERIOD_UNIT_CODE", length = 30)
    @Enumerated(EnumType.STRING)
    private TermUnit warningPeriodUnitCode;


    @Column(name = "REACTIVATION_METHOD_CODE", length = 30)
    @Enumerated(EnumType.STRING)
    private ReactivationMethod reactivationMethodCode;


    @Column(name = "AUTO_REACTIVATION_ALLOWED", nullable = false)
    private Boolean autoReactivationAllowed = false;


    @Column(name = "VALID_FROM")
    private LocalDate validFrom;


    @Column(name = "VALID_TO")
    private LocalDate validTo;
}
