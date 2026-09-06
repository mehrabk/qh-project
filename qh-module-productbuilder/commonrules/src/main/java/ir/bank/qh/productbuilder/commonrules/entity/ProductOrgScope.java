package ir.bank.qh.productbuilder.commonrules.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.common.enums.RecordStatus;
import ir.bank.qh.productbuilder.commonrules.enums.OrgUnitType;
import ir.bank.qh.productbuilder.core.entity.ProductVersion;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * Which organizational units (branches/regions) this product version is included in or excluded from.
 * Maps to table PRODUCT_ORG_SCOPE.
 */
@Getter
@Setter
@Entity
@Table(schema = "PRODUCTBUILDER", name = "product_org_scope")
public class ProductOrgScope extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_VERSION_ID", nullable = false)
    private ProductVersion productVersion;


    @Column(name = "ORG_UNIT_TYPE_CODE", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private OrgUnitType orgUnitTypeCode;


    @Column(name = "ORG_UNIT_ID")
    private Long orgUnitId;


    @Column(name = "ORG_UNIT_CODE", length = 50)
    private String orgUnitCode;


    @Column(name = "IS_ALLOWED", nullable = false)
    private Boolean isAllowed = true;


    @Column(name = "VALID_FROM")
    private LocalDate validFrom;


    @Column(name = "VALID_TO")
    private LocalDate validTo;


    @Column(name = "RULE_STATUS_CODE", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private RecordStatus ruleStatusCode = RecordStatus.ACTIVE;
}
