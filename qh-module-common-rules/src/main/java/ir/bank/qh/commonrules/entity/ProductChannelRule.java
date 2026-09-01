package ir.bank.qh.commonrules.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.core.entity.ProductVersion;
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
@Table(schema = "COMMONRULES", name = "product_channel_rule")
public class ProductChannelRule extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_version_id", nullable = false)
    private ProductVersion productVersion;


    @Column(name = "channel_code", nullable = false, length = 30)
    private String channelCode;


    @Column(name = "is_allowed", nullable = false)
    private Boolean isAllowed = true;


    @Column(name = "valid_from")
    private LocalDate validFrom;


    @Column(name = "valid_to")
    private LocalDate validTo;


    @Column(name = "rule_status_code", nullable = false, length = 20)
    private String ruleStatusCode = "ACTIVE";
}
