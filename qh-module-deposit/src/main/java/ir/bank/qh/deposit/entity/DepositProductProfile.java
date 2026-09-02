package ir.bank.qh.deposit.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
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
@Table(schema = "PRODUCTBUILDER", name = "deposit_product_profile")
public class DepositProductProfile extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_VERSION_ID", nullable = false, unique = true)
    private ProductVersion productVersion;


    @Column(name = "DEPOSIT_GROUP_CODE", nullable = false, length = 30)
    private String depositGroupCode;


    @Column(name = "DEPOSIT_TYPE_CODE", nullable = false, length = 30)
    private String depositTypeCode;
}
