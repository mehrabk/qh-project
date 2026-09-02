package ir.bank.qh.productbuilder.commonrules.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.productbuilder.core.entity.ProductVersion;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * Which delivery channels (branch, mobile, internet banking...) this product version is offered/serviced on.
 * Maps to table PRODUCT_CHANNEL_RULE.
 */
@Getter
@Setter
@Entity
@Table(schema = "PRODUCTBUILDER", name = "product_channel_rule")
public class ProductChannelRule extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_VERSION_ID", nullable = false)
    private ProductVersion productVersion;


    @Column(name = "CHANNEL_CODE", nullable = false, length = 30)
    private String channelCode;


    @Column(name = "IS_ALLOWED", nullable = false)
    private Boolean isAllowed = true;


    @Column(name = "VALID_FROM")
    private LocalDate validFrom;


    @Column(name = "VALID_TO")
    private LocalDate validTo;


    @Column(name = "RULE_STATUS_CODE", nullable = false, length = 20)
    private String ruleStatusCode = "ACTIVE";
}
