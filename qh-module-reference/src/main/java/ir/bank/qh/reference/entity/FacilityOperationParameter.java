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
@Table(schema = "PRODUCTBUILDER", name = "facility_operation_parameter")
public class FacilityOperationParameter extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;



    @Column(name = "OVERDUE_DURATION")
    private Integer overdueDuration;


    @Column(name = "PASTDUE_DURATION")
    private Integer pastdueDuration;


    @Column(name = "DOUBTFUL_DURATION")
    private Integer doubtfulDuration;


    @Column(name = "TRANSFER_CATEGORY_METHOD")
    private Integer transferCategoryMethod;


    @Column(name = "TRANSFER_PRINCIPLE")
    private Integer transferPrinciple;


    @Column(name = "TRANSFER_PENALTY")
    private Integer transferPenalty;


    @Column(name = "TRANSFER_COMMISSION")
    private Integer transferCommission;


    @Column(name = "PARAMETER_STATUS_CODE", nullable = false, length = 20)
    private String parameterStatusCode = "ACTIVE";
}
