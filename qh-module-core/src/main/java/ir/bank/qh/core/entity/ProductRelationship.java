package ir.bank.qh.core.entity;

import ir.bank.qh.common.entity.BaseAuditableEntity;
import ir.bank.qh.core.entity.Product;
import ir.bank.qh.core.entity.ProductVersion;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * Relationship between products, e.g. REQUIRES / BACKED_BY, with optional amount range.
 * Maps to table PRODUCT_RELATIONSHIP.
 */
@Getter
@Setter
@Entity
@Table(schema = "CORE", name = "product_relationship")
public class ProductRelationship extends BaseAuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_product_version_id", nullable = false)
    private ProductVersion sourceProductVersion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_product_id", nullable = false)
    private Product targetProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_product_version_id")
    private ProductVersion targetProductVersion;


    @Column(name = "relationship_type_code", nullable = false, length = 30)
    private String relationshipTypeCode;


    @Column(name = "priority_no", nullable = false)
    private Integer priorityNo = 1;


    @Column(name = "is_mandatory", nullable = false)
    private Boolean isMandatory = false;


    @Column(name = "min_relation_amount", precision = 20, scale = 4)
    private BigDecimal minRelationAmount;


    @Column(name = "max_relation_amount", precision = 20, scale = 4)
    private BigDecimal maxRelationAmount;


    @Column(name = "record_status_code", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
