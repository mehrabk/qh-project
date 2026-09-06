package ir.bank.qh.productbuilder.core.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.common.enums.RecordStatus;
import ir.bank.qh.productbuilder.core.enums.ApprovalStatus;
import ir.bank.qh.productbuilder.core.enums.EnableStatus;
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
@Table(schema = "PRODUCTBUILDER", name = "product_version")
public class ProductVersion extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOURCE_VERSION_ID")
    private ProductVersion sourceVersion;


    @Column(name = "VERSION_NO", nullable = false)
    private Integer versionNo;


    @Column(name = "VALID_FROM")
    private LocalDate validFrom;


    @Column(name = "VALID_TO")
    private LocalDate validTo;


    @Column(name = "VERSION_STATUS_CODE", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private ApprovalStatus versionStatusCode = ApprovalStatus.DRAFT;


    @Column(name = "IS_CURRENT", nullable = false)
    private Boolean isCurrent = false;


    @Column(name = "ORIGINATION_STATUS_CODE", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private EnableStatus originationStatusCode = EnableStatus.DISABLED;


    @Column(name = "SERVICING_STATUS_CODE", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private EnableStatus servicingStatusCode = EnableStatus.DISABLED;


    @Column(name = "CHANGE_REASON", length = 500)
    private String changeReason;


    @Column(name = "APPROVED_AT")
    private LocalDateTime approvedAt;


    @Column(name = "APPROVED_BY", length = 100)
    private String approvedBy;


    @Column(name = "RECORD_STATUS_CODE", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private RecordStatus recordStatusCode = RecordStatus.ACTIVE;
}
