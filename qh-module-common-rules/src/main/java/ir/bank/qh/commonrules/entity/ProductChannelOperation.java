package ir.bank.qh.commonrules.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.commonrules.entity.ProductChannelRule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Which operations (OPEN, APPLY, SERVICE, CLOSE...) are allowed within a given channel rule.
 * Maps to table PRODUCT_CHANNEL_OPERATION.
 */
@Getter
@Setter
@Entity
@Table(schema = "COMMONRULES", name = "product_channel_operation")
public class ProductChannelOperation extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_rule_id", nullable = false)
    private ProductChannelRule channelRule;


    @Column(name = "operation_code", nullable = false, length = 40)
    private String operationCode;


    @Column(name = "is_allowed", nullable = false)
    private Boolean isAllowed = true;


    @Column(name = "requires_additional_auth", nullable = false)
    private Boolean requiresAdditionalAuth = false;


    @Column(name = "record_status_code", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
