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
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_VERSION_ID", nullable = false)
    private ProductVersion productVersion;


    @Column(name = "WITHDRAWAL_MEDIA_CODE", nullable = false, length = 30)
    private String withdrawalMediaCode;


    @Column(name = "CHANNEL_CODE", length = 30)
    private String channelCode;


    @Column(name = "IS_ALLOWED", nullable = false)
    private Boolean isAllowed = true;


    @Column(name = "MAX_TRANSACTION_AMOUNT", precision = 20, scale = 4)
    private BigDecimal maxTransactionAmount;


    @Column(name = "DAILY_AMOUNT_LIMIT", precision = 20, scale = 4)
    private BigDecimal dailyAmountLimit;


    @Column(name = "DAILY_COUNT_LIMIT")
    private Integer dailyCountLimit;
}
