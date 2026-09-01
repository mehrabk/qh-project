package ir.bank.qh.deposit.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.deposit.entity.DepositProductClosureRule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * How the remaining balance is settled (component, method, destination) as part of a closure rule.
 * Maps to table DEPOSIT_PRODUCT_CLOSURE_SETTLEMENT_RULE.
 */
@Getter
@Setter
@Entity
@Table(schema = "DEPOSIT", name = "deposit_product_closure_settlement_rule")
public class DepositProductClosureSettlementRule extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "closure_rule_id", nullable = false)
    private DepositProductClosureRule closureRule;


    @Column(name = "settlement_component_code", nullable = false, length = 40)
    private String settlementComponentCode;


    @Column(name = "settlement_method_code", length = 40)
    private String settlementMethodCode;


    @Column(name = "destination_code", length = 30)
    private String destinationCode;


    @Column(name = "is_mandatory", nullable = false)
    private Boolean isMandatory = true;


    @Column(name = "sequence_no", nullable = false)
    private Integer sequenceNo = 1;


    @Column(name = "description", length = 500)
    private String description;
}
