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
    @Column(name = "id")
    private Long id;



    @Column(name = "operation_code", nullable = false, unique = true, length = 30)
    private String operationCode;


    @Column(name = "operation_name", nullable = false, length = 150)
    private String operationName;


    @Column(name = "operation_type_code", length = 30)
    private String operationTypeCode;


    @Column(name = "facility_step")
    private Integer facilityStep;


    @Column(name = "record_status_code", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
