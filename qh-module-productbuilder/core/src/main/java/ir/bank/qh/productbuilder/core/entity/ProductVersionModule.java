package ir.bank.qh.productbuilder.core.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Which business modules (DEPOSIT, LOAN, ...) are enabled/configured for a given product version.
 * Maps to table PRODUCT_VERSION_MODULE.
 */
@Getter
@Setter
@Entity
@Table(schema = "PRODUCTBUILDER", name = "product_version_module")
public class ProductVersionModule extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_VERSION_ID", nullable = false)
    private ProductVersion productVersion;


    @Column(name = "MODULE_CODE", nullable = false, length = 40)
    private String moduleCode;


    @Column(name = "IS_ENABLED", nullable = false)
    private Boolean isEnabled = true;


    @Column(name = "CONFIGURATION_STATUS_CODE", nullable = false, length = 30)
    private String configurationStatusCode = "NOT_CONFIGURED";


    @Column(name = "VALIDATION_STATUS_CODE", nullable = false, length = 30)
    private String validationStatusCode = "NOT_VALIDATED";


    @Lob
    @Column(name = "CONFIG_JSON")
    private String configJson;
}
