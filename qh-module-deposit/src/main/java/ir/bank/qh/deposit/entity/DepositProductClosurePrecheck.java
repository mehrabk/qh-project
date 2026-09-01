package ir.bank.qh.deposit.entity;

import ir.bank.qh.common.entity.BaseAuditableEntity;
import ir.bank.qh.deposit.entity.DepositProductClosureRule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * A pre-closure check (e.g. no open debt) that must pass before a closure rule can proceed.
 * Maps to table DEPOSIT_PRODUCT_CLOSURE_PRECHECK.
 */
@Getter
@Setter
@Entity
@Table(schema = "DEPOSIT", name = "deposit_product_closure_precheck")
public class DepositProductClosurePrecheck extends BaseAuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "closure_rule_id", nullable = false)
    private DepositProductClosureRule closureRule;


    @Column(name = "check_type_code", nullable = false, length = 40)
    private String checkTypeCode;


    @Column(name = "is_mandatory", nullable = false)
    private Boolean isMandatory = true;


    @Column(name = "failure_action_code", length = 30)
    private String failureActionCode;


    @Column(name = "sequence_no", nullable = false)
    private Integer sequenceNo = 1;


    @Column(name = "description", length = 500)
    private String description;
}
