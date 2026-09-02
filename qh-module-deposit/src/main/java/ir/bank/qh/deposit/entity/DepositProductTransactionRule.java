package ir.bank.qh.deposit.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
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
public class DepositProductTransactionRule extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @Column(name = "PRODUCT_VERSION_ID", nullable = false)
    private Long productVersionId;


    @Column(name = "TRANSACTION_TYPE_CODE", nullable = false, length = 30)
    private String transactionTypeCode;


    @Column(name = "IS_ALLOWED", nullable = false)
    private Boolean isAllowed = true;


    @Column(name = "MIN_TRANSACTION_AMOUNT", precision = 20, scale = 4)
    private BigDecimal minTransactionAmount;


    @Column(name = "MAX_TRANSACTION_AMOUNT", precision = 20, scale = 4)
    private BigDecimal maxTransactionAmount;


    @Column(name = "DAILY_AMOUNT_LIMIT", precision = 20, scale = 4)
    private BigDecimal dailyAmountLimit;


    @Column(name = "DAILY_COUNT_LIMIT")
    private Integer dailyCountLimit;


    @Column(name = "MONTHLY_AMOUNT_LIMIT", precision = 20, scale = 4)
    private BigDecimal monthlyAmountLimit;


    @Column(name = "MONTHLY_COUNT_LIMIT")
    private Integer monthlyCountLimit;
}
