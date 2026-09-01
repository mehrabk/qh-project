package ir.bank.qh.reference.entity;

import ir.bank.qh.common.entity.BaseEntity;
import ir.bank.qh.reference.entity.PatternOperation;
import ir.bank.qh.reference.entity.SubOperation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Sub-operation detail attached to a pattern operation step.
 * Maps to table PATTERN_OPERATION_DETAIL.
 */
@Getter
@Setter
@Entity
@Table(schema = "REFERENCE", name = "pattern_operation_detail")
public class PatternOperationDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PATTERN_OPERATION_ID", nullable = false)
    private PatternOperation patternOperation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUB_OPERATION_ID", nullable = false)
    private SubOperation subOperation;


    @Column(name = "RECORD_STATUS_CODE", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
