package ir.bank.qh.commonrules.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.core.entity.ProductVersion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Mandatory external inquiries (e.g. bounced-cheque check, credit bureau) required at a process stage.
 * Maps to table PRODUCT_REQUIRED_INQUIRY.
 */
@Getter
@Setter
@Entity
@Table(schema = "COMMONRULES", name = "product_required_inquiry")
public class ProductRequiredInquiry extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_version_id", nullable = false)
    private ProductVersion productVersion;


    @Column(name = "inquiry_type_code", nullable = false, length = 30)
    private String inquiryTypeCode;


    @Column(name = "requirement_stage_code", length = 30)
    private String requirementStageCode;


    @Column(name = "max_result_age_minutes")
    private Integer maxResultAgeMinutes;


    @Column(name = "is_mandatory", nullable = false)
    private Boolean isMandatory = true;


    @Column(name = "rule_status_code", nullable = false, length = 20)
    private String ruleStatusCode = "ACTIVE";
}
