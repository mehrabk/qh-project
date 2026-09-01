package ir.bank.qh.reference.entity;

import ir.bank.qh.common.entity.BaseEntity;
import ir.bank.qh.reference.entity.Operation;
import ir.bank.qh.reference.entity.PatternOperationLoanType;
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
    @Column(name = "id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pattern_operation_loan_type_id", nullable = false)
    private PatternOperationLoanType patternOperationLoanType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operation_id", nullable = false)
    private Operation operation;


    @Column(name = "operation_order")
    private Integer operationOrder;


    @Column(name = "record_status_code", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
