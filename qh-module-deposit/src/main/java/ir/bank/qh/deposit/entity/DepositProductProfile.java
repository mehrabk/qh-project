package ir.bank.qh.deposit.entity;

import ir.bank.qh.common.entity.BaseAuditableEntity;
import ir.bank.qh.core.entity.ProductVersion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Specialised deposit identity for a product version: deposit group and type (e.g. QH_SAVINGS).
 * Maps to table DEPOSIT_PRODUCT_PROFILE.
 */
@Getter
@Setter
@Entity
@Table(schema = "DEPOSIT", name = "deposit_product_profile")
public class DepositProductProfile extends BaseAuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_version_id", nullable = false, unique = true)
    private ProductVersion productVersion;


    @Column(name = "deposit_group_code", nullable = false, length = 30)
    private String depositGroupCode;


    @Column(name = "deposit_type_code", nullable = false, length = 30)
    private String depositTypeCode;
}
