package ir.bank.qh.productbuilder.commonrules.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.common.enums.RecordStatus;
import ir.bank.qh.productbuilder.commonrules.enums.InquiryType;
import ir.bank.qh.productbuilder.commonrules.enums.RequirementStage;
import ir.bank.qh.productbuilder.core.entity.ProductVersion;
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
@Table(schema = "PRODUCTBUILDER", name = "product_required_inquiry")
public class ProductRequiredInquiry extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_VERSION_ID", nullable = false)
    private ProductVersion productVersion;


    @Column(name = "INQUIRY_TYPE_CODE", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private InquiryType inquiryTypeCode;


    @Column(name = "REQUIREMENT_STAGE_CODE", length = 30)
    @Enumerated(EnumType.STRING)
    private RequirementStage requirementStageCode;


    @Column(name = "MAX_RESULT_AGE_MINUTES")
    private Integer maxResultAgeMinutes;


    @Column(name = "IS_MANDATORY", nullable = false)
    private Boolean isMandatory = true;


    @Column(name = "RULE_STATUS_CODE", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private RecordStatus ruleStatusCode = RecordStatus.ACTIVE;
}
