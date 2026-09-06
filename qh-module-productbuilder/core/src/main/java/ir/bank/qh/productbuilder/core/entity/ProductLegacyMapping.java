package ir.bank.qh.productbuilder.core.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.productbuilder.core.enums.ProductDomain;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * Traceability mapping between a new PRODUCT/PRODUCT_VERSION and the legacy source record it was migrated from.
 * Maps to table PRODUCT_LEGACY_MAPPING.
 */
@Getter
@Setter
@Entity
@Table(schema = "PRODUCTBUILDER", name = "product_legacy_mapping")
public class ProductLegacyMapping extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_VERSION_ID", nullable = false)
    private ProductVersion productVersion;


    @Column(name = "SOURCE_DOMAIN_CODE", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private ProductDomain sourceDomainCode;


    @Column(name = "SOURCE_TABLE_NAME", nullable = false, length = 100)
    private String sourceTableName;


    @Column(name = "SOURCE_RECORD_ID", length = 100)
    private String sourceRecordId;


    @Column(name = "SOURCE_VERSION_ID", length = 100)
    private String sourceVersionId;


    @Column(name = "MIGRATION_BATCH_ID", length = 100)
    private String migrationBatchId;


    @Column(name = "MIGRATED_AT")
    private LocalDateTime migratedAt;
}
