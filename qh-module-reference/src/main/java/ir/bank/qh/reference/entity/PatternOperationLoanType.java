package ir.bank.qh.reference.entity;

import ir.bank.qh.common.entity.BaseEntity;
import ir.bank.qh.reference.entity.LoanType;
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
    @Column(name = "id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_type_id", nullable = false)
    private LoanType loanType;


    @Column(name = "pattern_operation_version")
    private Integer patternOperationVersion;


    @Column(name = "pattern_name", length = 100)
    private String patternName;


    @Column(name = "record_status_code", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
