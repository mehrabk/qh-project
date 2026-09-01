package ir.bank.qh.deposit.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.core.entity.ProductVersion;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * Whether/how funds can be placed on hold (full/partial blocking) for this deposit product.
 * Maps to table DEPOSIT_PRODUCT_HOLD_RULE.
 */
@Getter
@Setter
@Entity
@Table(schema = "DEPOSIT", name = "deposit_product_hold_rule")
public class DepositProductHoldRule extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_version_id", nullable = false)
    private ProductVersion productVersion;


    @Column(name = "hold_type_code", nullable = false, length = 30)
    private String holdTypeCode;


    @Column(name = "is_supported", nullable = false)
    private Boolean isSupported = true;


    @Column(name = "min_hold_amount", precision = 20, scale = 4)
    private BigDecimal minHoldAmount;


    @Column(name = "max_hold_amount", precision = 20, scale = 4)
    private BigDecimal maxHoldAmount;
}
