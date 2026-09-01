package ir.bank.qh.deposit.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.core.entity.ProductVersion;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * Allowed withdrawal instruments (debit card, cheque book, branch instruction...) and their limits.
 * Maps to table DEPOSIT_PRODUCT_WITHDRAWAL_MEDIA.
 */
@Getter
@Setter
@Entity
@Table(schema = "DEPOSIT", name = "deposit_product_withdrawal_media")
public class DepositProductWithdrawalMedia extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_version_id", nullable = false)
    private ProductVersion productVersion;


    @Column(name = "withdrawal_media_code", nullable = false, length = 30)
    private String withdrawalMediaCode;


    @Column(name = "channel_code", length = 30)
    private String channelCode;


    @Column(name = "is_allowed", nullable = false)
    private Boolean isAllowed = true;


    @Column(name = "max_transaction_amount", precision = 20, scale = 4)
    private BigDecimal maxTransactionAmount;


    @Column(name = "daily_amount_limit", precision = 20, scale = 4)
    private BigDecimal dailyAmountLimit;


    @Column(name = "daily_count_limit")
    private Integer dailyCountLimit;
}
