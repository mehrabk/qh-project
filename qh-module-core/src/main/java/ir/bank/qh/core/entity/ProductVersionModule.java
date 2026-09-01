package ir.bank.qh.core.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.core.entity.ProductVersion;
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
@Table(schema = "CORE", name = "product_version_module")
public class ProductVersionModule extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_version_id", nullable = false)
    private ProductVersion productVersion;


    @Column(name = "module_code", nullable = false, length = 40)
    private String moduleCode;


    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled = true;


    @Column(name = "configuration_status_code", nullable = false, length = 30)
    private String configurationStatusCode = "NOT_CONFIGURED";


    @Column(name = "validation_status_code", nullable = false, length = 30)
    private String validationStatusCode = "NOT_VALIDATED";


    @Lob
    @Column(name = "config_json")
    private String configJson;
}
