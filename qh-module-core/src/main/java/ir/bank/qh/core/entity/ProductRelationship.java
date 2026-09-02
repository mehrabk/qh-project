package ir.bank.qh.core.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
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
@Table(schema = "PRODUCTBUILDER", name = "product_relationship")
public class ProductRelationship extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOURCE_PRODUCT_VERSION_ID", nullable = false)
    private ProductVersion sourceProductVersion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TARGET_PRODUCT_ID", nullable = false)
    private Product targetProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TARGET_PRODUCT_VERSION_ID")
    private ProductVersion targetProductVersion;


    @Column(name = "RELATIONSHIP_TYPE_CODE", nullable = false, length = 30)
    private String relationshipTypeCode;


    @Column(name = "PRIORITY_NO", nullable = false)
    private Integer priorityNo = 1;


    @Column(name = "IS_MANDATORY", nullable = false)
    private Boolean isMandatory = false;


    @Column(name = "MIN_RELATION_AMOUNT", precision = 20, scale = 4)
    private BigDecimal minRelationAmount;


    @Column(name = "MAX_RELATION_AMOUNT", precision = 20, scale = 4)
    private BigDecimal maxRelationAmount;


    @Column(name = "RECORD_STATUS_CODE", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
