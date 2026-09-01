package ir.bank.qh.loan.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.core.entity.ProductVersion;
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
@Table(schema = "LOAN", name = "loan_product_process_rule")
public class LoanProductProcessRule extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_version_id", nullable = false)
    private ProductVersion productVersion;


    @Column(name = "application_validity_days")
    private Integer applicationValidityDays;


    @Column(name = "approval_validity_days")
    private Integer approvalValidityDays;


    @Column(name = "contract_validity_days")
    private Integer contractValidityDays;


    @Column(name = "manual_approval_required", nullable = false)
    private Boolean manualApprovalRequired = false;


    @Column(name = "committee_approval_required", nullable = false)
    private Boolean committeeApprovalRequired = false;


    @Column(name = "multi_level_approval_required", nullable = false)
    private Boolean multiLevelApprovalRequired = false;


    @Column(name = "cbi_tracking_required", nullable = false)
    private Boolean cbiTrackingRequired = false;


    @Column(name = "external_inquiry_required", nullable = false)
    private Boolean externalInquiryRequired = false;


    @Column(name = "allow_override", nullable = false)
    private Boolean allowOverride = false;


    @Column(name = "override_approval_level_code", length = 30)
    private String overrideApprovalLevelCode;


    @Column(name = "valid_from")
    private LocalDate validFrom;


    @Column(name = "record_status_code", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
