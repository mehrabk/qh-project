package ir.bank.qh.core.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.core.entity.Product;
import ir.bank.qh.core.entity.ProductVersion;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * A published version (snapshot) of a PRODUCT. All rule tables hang off PRODUCT_VERSION_ID, not PRODUCT_ID.
 * Maps to table PRODUCT_VERSION.
 */
@Getter
@Setter
@Entity
@Table(schema = "CORE", name = "product_version")
public class ProductVersion extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_version_id")
    private ProductVersion sourceVersion;


    @Column(name = "version_no", nullable = false)
    private Integer versionNo;


    @Column(name = "valid_from")
    private LocalDate validFrom;


    @Column(name = "valid_to")
    private LocalDate validTo;


    @Column(name = "version_status_code", nullable = false, length = 30)
    private String versionStatusCode = "DRAFT";


    @Column(name = "is_current", nullable = false)
    private Boolean isCurrent = false;


    @Column(name = "origination_status_code", nullable = false, length = 30)
    private String originationStatusCode = "DISABLED";


    @Column(name = "servicing_status_code", nullable = false, length = 30)
    private String servicingStatusCode = "DISABLED";


    @Column(name = "change_reason", length = 500)
    private String changeReason;


    @Column(name = "approved_at")
    private LocalDateTime approvedAt;


    @Column(name = "approved_by", length = 100)
    private String approvedBy;


    @Column(name = "record_status_code", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
