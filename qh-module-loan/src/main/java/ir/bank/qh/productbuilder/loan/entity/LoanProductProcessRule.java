package ir.bank.qh.productbuilder.loan.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.productbuilder.core.entity.ProductVersion;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * Validity windows and approval-workflow policy for origination/servicing of a loan product version.
 * Maps to table LOAN_PRODUCT_PROCESS_RULE.
 */
@Getter
@Setter
@Entity
@Table(schema = "PRODUCTBUILDER", name = "loan_product_process_rule")
public class LoanProductProcessRule extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_VERSION_ID", nullable = false)
    private ProductVersion productVersion;


    @Column(name = "APPLICATION_VALIDITY_DAYS")
    private Integer applicationValidityDays;


    @Column(name = "APPROVAL_VALIDITY_DAYS")
    private Integer approvalValidityDays;


    @Column(name = "CONTRACT_VALIDITY_DAYS")
    private Integer contractValidityDays;


    @Column(name = "MANUAL_APPROVAL_REQUIRED", nullable = false)
    private Boolean manualApprovalRequired = false;


    @Column(name = "COMMITTEE_APPROVAL_REQUIRED", nullable = false)
    private Boolean committeeApprovalRequired = false;


    @Column(name = "MULTI_LEVEL_APPROVAL_REQUIRED", nullable = false)
    private Boolean multiLevelApprovalRequired = false;


    @Column(name = "CBI_TRACKING_REQUIRED", nullable = false)
    private Boolean cbiTrackingRequired = false;


    @Column(name = "EXTERNAL_INQUIRY_REQUIRED", nullable = false)
    private Boolean externalInquiryRequired = false;


    @Column(name = "ALLOW_OVERRIDE", nullable = false)
    private Boolean allowOverride = false;


    @Column(name = "OVERRIDE_APPROVAL_LEVEL_CODE", length = 30)
    private String overrideApprovalLevelCode;


    @Column(name = "VALID_FROM")
    private LocalDate validFrom;


    @Column(name = "RECORD_STATUS_CODE", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
