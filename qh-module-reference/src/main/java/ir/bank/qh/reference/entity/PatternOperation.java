package ir.bank.qh.reference.entity;

import ir.bank.qh.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * An ordered operation step within a loan-type accounting pattern.
 * Maps to table PATTERN_OPERATION.
 */
@Getter
@Setter
@Entity
@Table(schema = "REFERENCE", name = "pattern_operation")
public class PatternOperation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @Column(name = "PATTERN_OPERATION_LOAN_TYPE_ID", nullable = false)
    private Long patternOperationLoanTypeId;

    @Column(name = "OPERATION_ID", nullable = false)
    private Long operationId;


    @Column(name = "OPERATION_ORDER")
    private Integer operationOrder;


    @Column(name = "RECORD_STATUS_CODE", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
