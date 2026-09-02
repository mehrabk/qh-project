package ir.bank.qh.deposit.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
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
public class DepositProductClosurePrecheck extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLOSURE_RULE_ID", nullable = false)
    private DepositProductClosureRule closureRule;


    @Column(name = "CHECK_TYPE_CODE", nullable = false, length = 40)
    private String checkTypeCode;


    @Column(name = "IS_MANDATORY", nullable = false)
    private Boolean isMandatory = true;


    @Column(name = "FAILURE_ACTION_CODE", length = 30)
    private String failureActionCode;


    @Column(name = "SEQUENCE_NO", nullable = false)
    private Integer sequenceNo = 1;


    @Column(name = "DESCRIPTION", length = 500)
    private String description;
}
