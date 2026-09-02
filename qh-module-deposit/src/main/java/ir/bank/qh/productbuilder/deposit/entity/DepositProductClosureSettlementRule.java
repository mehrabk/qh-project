package ir.bank.qh.productbuilder.deposit.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
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
@Table(schema = "PRODUCTBUILDER", name = "deposit_product_closure_settlement_rule")
public class DepositProductClosureSettlementRule extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLOSURE_RULE_ID", nullable = false)
    private DepositProductClosureRule closureRule;


    @Column(name = "SETTLEMENT_COMPONENT_CODE", nullable = false, length = 40)
    private String settlementComponentCode;


    @Column(name = "SETTLEMENT_METHOD_CODE", length = 40)
    private String settlementMethodCode;


    @Column(name = "DESTINATION_CODE", length = 30)
    private String destinationCode;


    @Column(name = "IS_MANDATORY", nullable = false)
    private Boolean isMandatory = true;


    @Column(name = "SEQUENCE_NO", nullable = false)
    private Integer sequenceNo = 1;


    @Column(name = "DESCRIPTION", length = 500)
    private String description;
}
