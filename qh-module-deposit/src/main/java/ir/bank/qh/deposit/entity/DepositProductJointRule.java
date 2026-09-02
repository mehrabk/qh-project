package ir.bank.qh.deposit.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.core.entity.ProductVersion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Joint-ownership rules: ownership type, min/max joint owners, signing rule, minimum required signers.
 * Maps to table DEPOSIT_PRODUCT_JOINT_RULE.
 */
@Getter
@Setter
@Entity
@Table(schema = "PRODUCTBUILDER", name = "deposit_product_joint_rule")
public class DepositProductJointRule extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_VERSION_ID", nullable = false)
    private ProductVersion productVersion;


    @Column(name = "OWNERSHIP_TYPE_CODE", length = 30)
    private String ownershipTypeCode;


    @Column(name = "MIN_JOINT_OWNER_COUNT")
    private Integer minJointOwnerCount;


    @Column(name = "MAX_JOINT_OWNER_COUNT")
    private Integer maxJointOwnerCount;


    @Column(name = "SIGNING_RULE_CODE", length = 30)
    private String signingRuleCode;


    @Column(name = "MIN_REQUIRED_SIGNER_COUNT")
    private Integer minRequiredSignerCount;
}
