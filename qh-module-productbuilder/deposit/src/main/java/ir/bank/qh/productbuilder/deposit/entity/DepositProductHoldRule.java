package ir.bank.qh.productbuilder.deposit.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.productbuilder.core.entity.ProductVersion;
import ir.bank.qh.productbuilder.deposit.enums.HoldType;
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
@Table(schema = "PRODUCTBUILDER", name = "deposit_product_hold_rule")
public class DepositProductHoldRule extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_VERSION_ID", nullable = false)
    private ProductVersion productVersion;


    @Column(name = "HOLD_TYPE_CODE", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private HoldType holdTypeCode;


    @Column(name = "IS_SUPPORTED", nullable = false)
    private Boolean isSupported = true;


    @Column(name = "MIN_HOLD_AMOUNT", precision = 20, scale = 4)
    private BigDecimal minHoldAmount;


    @Column(name = "MAX_HOLD_AMOUNT", precision = 20, scale = 4)
    private BigDecimal maxHoldAmount;
}
