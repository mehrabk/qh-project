package ir.bank.qh.core.entity;

import ir.bank.qh.common.entity.BaseAuditableEntity;
import ir.bank.qh.core.entity.Product;
import ir.bank.qh.core.entity.ProductVersion;
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
@Table(schema = "CORE", name = "product_legacy_mapping")
public class ProductLegacyMapping extends BaseAuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_version_id", nullable = false)
    private ProductVersion productVersion;


    @Column(name = "source_domain_code", nullable = false, length = 30)
    private String sourceDomainCode;


    @Column(name = "source_table_name", nullable = false, length = 100)
    private String sourceTableName;


    @Column(name = "source_record_id", length = 100)
    private String sourceRecordId;


    @Column(name = "source_version_id", length = 100)
    private String sourceVersionId;


    @Column(name = "migration_batch_id", length = 100)
    private String migrationBatchId;


    @Column(name = "migrated_at")
    private LocalDateTime migratedAt;
}
