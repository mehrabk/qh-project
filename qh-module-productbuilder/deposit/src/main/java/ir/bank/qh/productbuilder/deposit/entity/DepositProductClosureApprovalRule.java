package ir.bank.qh.productbuilder.deposit.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.common.enums.ApprovalLevel;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * Approval level(s) required (with amount thresholds) before a closure can be executed.
 * Maps to table DEPOSIT_PRODUCT_CLOSURE_APPROVAL_RULE.
 */
@Getter
@Setter
@Entity
@Table(schema = "PRODUCTBUILDER", name = "deposit_product_closure_approval_rule")
public class DepositProductClosureApprovalRule extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLOSURE_RULE_ID", nullable = false)
    private DepositProductClosureRule closureRule;


    @Column(name = "APPROVAL_LEVEL_CODE", nullable = false, length = 40)
    @Enumerated(EnumType.STRING)
    private ApprovalLevel approvalLevelCode;


    @Column(name = "IS_MANDATORY", nullable = false)
    private Boolean isMandatory = true;


    @Column(name = "APPROVAL_SEQUENCE_NO", nullable = false)
    private Integer approvalSequenceNo = 1;


    @Column(name = "MIN_AMOUNT", precision = 20, scale = 4)
    private BigDecimal minAmount;


    @Column(name = "MAX_AMOUNT", precision = 20, scale = 4)
    private BigDecimal maxAmount;


    @Column(name = "DESCRIPTION", length = 500)
    private String description;
}
