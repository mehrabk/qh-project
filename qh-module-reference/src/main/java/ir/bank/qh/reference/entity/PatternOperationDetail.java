package ir.bank.qh.reference.entity;

import ir.bank.qh.common.entity.BaseAuditableEntity;
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
public class PatternOperationDetail extends BaseAuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pattern_operation_id", nullable = false)
    private PatternOperation patternOperation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_operation_id", nullable = false)
    private SubOperation subOperation;


    @Column(name = "record_status_code", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
