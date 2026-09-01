package ir.bank.qh.reference.entity;

import ir.bank.qh.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Reference catalog of high-level accounting operations, e.g. DISBURSEMENT, REPAYMENT, SETTLEMENT.
 * Maps to table OPERATION.
 */
@Getter
@Setter
@Entity
@Table(schema = "REFERENCE", name = "operation")
public class Operation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;



    @Column(name = "OPERATION_CODE", nullable = false, unique = true, length = 30)
    private String operationCode;


    @Column(name = "OPERATION_NAME", nullable = false, length = 150)
    private String operationName;


    @Column(name = "OPERATION_TYPE_CODE", length = 30)
    private String operationTypeCode;


    @Column(name = "FACILITY_STEP")
    private Integer facilityStep;


    @Column(name = "RECORD_STATUS_CODE", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
