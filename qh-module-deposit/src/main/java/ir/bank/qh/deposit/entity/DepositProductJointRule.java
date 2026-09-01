package ir.bank.qh.deposit.entity;

import ir.bank.qh.common.entity.BaseAuditableEntity;
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
@Table(schema = "DEPOSIT", name = "deposit_product_joint_rule")
public class DepositProductJointRule extends BaseAuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_version_id", nullable = false)
    private ProductVersion productVersion;


    @Column(name = "ownership_type_code", length = 30)
    private String ownershipTypeCode;


    @Column(name = "min_joint_owner_count")
    private Integer minJointOwnerCount;


    @Column(name = "max_joint_owner_count")
    private Integer maxJointOwnerCount;


    @Column(name = "signing_rule_code", length = 30)
    private String signingRuleCode;


    @Column(name = "min_required_signer_count")
    private Integer minRequiredSignerCount;
}
