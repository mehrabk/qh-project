package ir.bank.qh.commonrules.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
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
@Table(schema = "COMMONRULES", name = "product_org_scope")
public class ProductOrgScope extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @Column(name = "PRODUCT_VERSION_ID", nullable = false)
    private Long productVersionId;


    @Column(name = "ORG_UNIT_TYPE_CODE", nullable = false, length = 30)
    private String orgUnitTypeCode;


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
    private String ruleStatusCode = "ACTIVE";
}
