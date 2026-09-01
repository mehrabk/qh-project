package ir.bank.qh.deposit.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.deposit.entity.DepositProductClosureRule;
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
@Table(schema = "DEPOSIT", name = "deposit_product_closure_approval_rule")
public class DepositProductClosureApprovalRule extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "closure_rule_id", nullable = false)
    private DepositProductClosureRule closureRule;


    @Column(name = "approval_level_code", nullable = false, length = 40)
    private String approvalLevelCode;


    @Column(name = "is_mandatory", nullable = false)
    private Boolean isMandatory = true;


    @Column(name = "approval_sequence_no", nullable = false)
    private Integer approvalSequenceNo = 1;


    @Column(name = "min_amount", precision = 20, scale = 4)
    private BigDecimal minAmount;


    @Column(name = "max_amount", precision = 20, scale = 4)
    private BigDecimal maxAmount;


    @Column(name = "description", length = 500)
    private String description;
}
