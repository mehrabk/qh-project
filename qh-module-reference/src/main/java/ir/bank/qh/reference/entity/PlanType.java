package ir.bank.qh.reference.entity;

import ir.bank.qh.common.entity.BaseAuditableEntity;
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
@Table(schema = "REFERENCE", name = "plan_type")
public class PlanType extends BaseAuditableEntity {


    @Column(name = "plan_type_code", nullable = false, unique = true, length = 30)
    private String planTypeCode;


    @Column(name = "plan_type_name", nullable = false, length = 50)
    private String planTypeName;


    @Column(name = "plan_type_cbi_code", length = 30)
    private String planTypeCbiCode;


    @Column(name = "min_commission_rate", precision = 5, scale = 2)
    private BigDecimal minCommissionRate;


    @Column(name = "max_commission_rate", precision = 5, scale = 2)
    private BigDecimal maxCommissionRate;


    @Column(name = "min_penalty_rate", precision = 5, scale = 2)
    private BigDecimal minPenaltyRate;


    @Column(name = "max_penalty_rate", precision = 5, scale = 2)
    private BigDecimal maxPenaltyRate;


    @Column(name = "max_penalty_forgiveness_rate", precision = 5, scale = 2)
    private BigDecimal maxPenaltyForgivenessRate;


    @Column(name = "record_status_code", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
