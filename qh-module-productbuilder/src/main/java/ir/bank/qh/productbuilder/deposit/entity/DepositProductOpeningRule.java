package ir.bank.qh.productbuilder.deposit.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.productbuilder.core.entity.ProductVersion;
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
@Table(schema = "PRODUCTBUILDER", name = "deposit_product_opening_rule")
public class DepositProductOpeningRule extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_VERSION_ID", nullable = false)
    private ProductVersion productVersion;


    @Column(name = "MIN_OPENING_AMOUNT", precision = 20, scale = 4)
    private BigDecimal minOpeningAmount;


    @Column(name = "MAX_OPENING_AMOUNT", precision = 20, scale = 4)
    private BigDecimal maxOpeningAmount;


    @Column(name = "MIN_REQUIRED_BALANCE", precision = 20, scale = 4)
    private BigDecimal minRequiredBalance;


    @Column(name = "MAX_ALLOWED_BALANCE", precision = 20, scale = 4)
    private BigDecimal maxAllowedBalance;


    @Column(name = "MAX_CUSTOMER_ACCOUNT_COUNT")
    private Integer maxCustomerAccountCount;


    @Column(name = "MAX_BANK_ACCOUNT_COUNT")
    private Integer maxBankAccountCount;


    @Column(name = "IS_INTRODUCER_REQUIRED", nullable = false)
    private Boolean isIntroducerRequired = false;
}
