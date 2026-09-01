package ir.bank.qh.deposit.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.core.entity.ProductVersion;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * Opening constraints: min/max opening amount, required balance, account count caps, introducer requirement.
 * Maps to table DEPOSIT_PRODUCT_OPENING_RULE.
 */
@Getter
@Setter
@Entity
@Table(schema = "DEPOSIT", name = "deposit_product_opening_rule")
public class DepositProductOpeningRule extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_version_id", nullable = false)
    private ProductVersion productVersion;


    @Column(name = "min_opening_amount", precision = 20, scale = 4)
    private BigDecimal minOpeningAmount;


    @Column(name = "max_opening_amount", precision = 20, scale = 4)
    private BigDecimal maxOpeningAmount;


    @Column(name = "min_required_balance", precision = 20, scale = 4)
    private BigDecimal minRequiredBalance;


    @Column(name = "max_allowed_balance", precision = 20, scale = 4)
    private BigDecimal maxAllowedBalance;


    @Column(name = "max_customer_account_count")
    private Integer maxCustomerAccountCount;


    @Column(name = "max_bank_account_count")
    private Integer maxBankAccountCount;


    @Column(name = "is_introducer_required", nullable = false)
    private Boolean isIntroducerRequired = false;
}
