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
@Table(schema = "PRODUCTBUILDER", name = "pattern_operation")
public class PatternOperation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PATTERN_OPERATION_LOAN_TYPE_ID", nullable = false)
    private PatternOperationLoanType patternOperationLoanType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OPERATION_ID", nullable = false)
    private Operation operation;


    @Column(name = "OPERATION_ORDER")
    private Integer operationOrder;


    @Column(name = "RECORD_STATUS_CODE", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
