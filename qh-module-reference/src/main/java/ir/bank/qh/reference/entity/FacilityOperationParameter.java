package ir.bank.qh.reference.entity;

import ir.bank.qh.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Reference parameters controlling overdue/pastdue/doubtful ageing and transfer rules for facilities.
 * Maps to table FACILITY_OPERATION_PARAMETER.
 */
@Getter
@Setter
@Entity
@Table(schema = "REFERENCE", name = "facility_operation_parameter")
public class FacilityOperationParameter extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;



    @Column(name = "overdue_duration")
    private Integer overdueDuration;


    @Column(name = "pastdue_duration")
    private Integer pastdueDuration;


    @Column(name = "doubtful_duration")
    private Integer doubtfulDuration;


    @Column(name = "transfer_category_method")
    private Integer transferCategoryMethod;


    @Column(name = "transfer_principle")
    private Integer transferPrinciple;


    @Column(name = "transfer_penalty")
    private Integer transferPenalty;


    @Column(name = "transfer_commission")
    private Integer transferCommission;


    @Column(name = "parameter_status_code", nullable = false, length = 20)
    private String parameterStatusCode = "ACTIVE";
}
