package ir.bank.qh.productbuilder.commonrules.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
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
@Table(schema = "PRODUCTBUILDER", name = "product_channel_operation")
public class ProductChannelOperation extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHANNEL_RULE_ID", nullable = false)
    private ProductChannelRule channelRule;


    @Column(name = "OPERATION_CODE", nullable = false, length = 40)
    private String operationCode;


    @Column(name = "IS_ALLOWED", nullable = false)
    private Boolean isAllowed = true;


    @Column(name = "REQUIRES_ADDITIONAL_AUTH", nullable = false)
    private Boolean requiresAdditionalAuth = false;


    @Column(name = "RECORD_STATUS_CODE", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
