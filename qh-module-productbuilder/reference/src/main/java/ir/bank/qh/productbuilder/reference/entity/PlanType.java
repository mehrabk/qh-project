package ir.bank.qh.productbuilder.reference.entity;

import ir.bank.qh.common.entity.BaseEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * Reference catalog of facility plan types, e.g. QH_STANDARD, RETAIL_GOODS, SME_WORKING_CAPITAL.
 * Maps to table PLAN_TYPE.
 */
@Getter
@Setter
@Entity
@Table(schema = "PRODUCTBUILDER", name = "plan_type")
public class PlanType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;



    @Column(name = "PLAN_TYPE_CODE", nullable = false, unique = true, length = 30)
    private String planTypeCode;


    @Column(name = "PLAN_TYPE_NAME", nullable = false, length = 50)
    private String planTypeName;


    @Column(name = "PLAN_TYPE_CBI_CODE", length = 30)
    private String planTypeCbiCode;


    @Column(name = "MIN_COMMISSION_RATE", precision = 5, scale = 2)
    private BigDecimal minCommissionRate;


    @Column(name = "MAX_COMMISSION_RATE", precision = 5, scale = 2)
    private BigDecimal maxCommissionRate;


    @Column(name = "MIN_PENALTY_RATE", precision = 5, scale = 2)
    private BigDecimal minPenaltyRate;


    @Column(name = "MAX_PENALTY_RATE", precision = 5, scale = 2)
    private BigDecimal maxPenaltyRate;


    @Column(name = "MAX_PENALTY_FORGIVENESS_RATE", precision = 5, scale = 2)
    private BigDecimal maxPenaltyForgivenessRate;


    @Column(name = "RECORD_STATUS_CODE", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
