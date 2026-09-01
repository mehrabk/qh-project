package ir.bank.qh.deposit.entity;

import ir.bank.qh.common.entity.BaseAuditableEntity;
import ir.bank.qh.core.entity.ProductVersion;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * Permission and limits (amount/count, daily/monthly) per transaction type on the deposit product.
 * Maps to table DEPOSIT_PRODUCT_TRANSACTION_RULE.
 */
@Getter
@Setter
@Entity
@Table(schema = "DEPOSIT", name = "deposit_product_transaction_rule")
public class DepositProductTransactionRule extends BaseAuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_version_id", nullable = false)
    private ProductVersion productVersion;


    @Column(name = "transaction_type_code", nullable = false, length = 30)
    private String transactionTypeCode;


    @Column(name = "is_allowed", nullable = false)
    private Boolean isAllowed = true;


    @Column(name = "min_transaction_amount", precision = 20, scale = 4)
    private BigDecimal minTransactionAmount;


    @Column(name = "max_transaction_amount", precision = 20, scale = 4)
    private BigDecimal maxTransactionAmount;


    @Column(name = "daily_amount_limit", precision = 20, scale = 4)
    private BigDecimal dailyAmountLimit;


    @Column(name = "daily_count_limit")
    private Integer dailyCountLimit;


    @Column(name = "monthly_amount_limit", precision = 20, scale = 4)
    private BigDecimal monthlyAmountLimit;


    @Column(name = "monthly_count_limit")
    private Integer monthlyCountLimit;
}
