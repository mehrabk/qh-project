package ir.bank.qh.commonrules.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.core.entity.ProductVersion;
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
    @Column(name = "id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_version_id", nullable = false)
    private ProductVersion productVersion;


    @Column(name = "org_unit_type_code", nullable = false, length = 30)
    private String orgUnitTypeCode;


    @Column(name = "org_unit_id")
    private Long orgUnitId;


    @Column(name = "org_unit_code", length = 50)
    private String orgUnitCode;


    @Column(name = "is_allowed", nullable = false)
    private Boolean isAllowed = true;


    @Column(name = "valid_from")
    private LocalDate validFrom;


    @Column(name = "valid_to")
    private LocalDate validTo;


    @Column(name = "rule_status_code", nullable = false, length = 20)
    private String ruleStatusCode = "ACTIVE";
}
