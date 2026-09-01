package ir.bank.qh.commonrules.entity;

import ir.bank.qh.common.entity.BaseAuditableEntity;
import ir.bank.qh.core.entity.ProductVersion;
import ir.bank.qh.reference.entity.DocumentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Mandatory/optional documents required at a given process stage, referencing DOCUMENT_TYPE.
 * Maps to table PRODUCT_REQUIRED_DOCUMENT.
 */
@Getter
@Setter
@Entity
@Table(schema = "COMMONRULES", name = "product_required_document")
public class ProductRequiredDocument extends BaseAuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_version_id", nullable = false)
    private ProductVersion productVersion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_type_id", nullable = false)
    private DocumentType documentType;


    @Column(name = "requirement_stage_code", nullable = false, length = 30)
    private String requirementStageCode;


    @Column(name = "process_step_no")
    private Integer processStepNo;


    @Column(name = "copy_count", nullable = false)
    private Integer copyCount = 1;


    @Column(name = "is_mandatory", nullable = false)
    private Boolean isMandatory = true;


    @Column(name = "rule_status_code", nullable = false, length = 20)
    private String ruleStatusCode = "ACTIVE";
}
