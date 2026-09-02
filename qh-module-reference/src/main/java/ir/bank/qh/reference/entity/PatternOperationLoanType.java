package ir.bank.qh.reference.entity;

import ir.bank.qh.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Defines an accounting-operation pattern (version) for a given loan type.
 * Maps to table PATTERN_OPERATION_LOAN_TYPE.
 */
@Getter
@Setter
@Entity
@Table(schema = "REFERENCE", name = "pattern_operation_loan_type")
public class PatternOperationLoanType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @Column(name = "LOAN_TYPE_ID", nullable = false)
    private Long loanTypeId;


    @Column(name = "PATTERN_OPERATION_VERSION")
    private Integer patternOperationVersion;


    @Column(name = "PATTERN_NAME", length = 100)
    private String patternName;


    @Column(name = "RECORD_STATUS_CODE", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
