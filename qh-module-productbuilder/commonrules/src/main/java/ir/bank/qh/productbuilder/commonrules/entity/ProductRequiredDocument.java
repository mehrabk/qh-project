package ir.bank.qh.productbuilder.commonrules.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.common.enums.RecordStatus;
import ir.bank.qh.productbuilder.commonrules.enums.RequirementStage;
import ir.bank.qh.productbuilder.core.entity.ProductVersion;
import ir.bank.qh.productbuilder.reference.entity.DocumentType;
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
@Table(schema = "PRODUCTBUILDER", name = "product_required_document")
public class ProductRequiredDocument extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_VERSION_ID", nullable = false)
    private ProductVersion productVersion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCUMENT_TYPE_ID", nullable = false)
    private DocumentType documentType;


    @Column(name = "REQUIREMENT_STAGE_CODE", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private RequirementStage requirementStageCode;


    @Column(name = "PROCESS_STEP_NO")
    private Integer processStepNo;


    @Column(name = "COPY_COUNT", nullable = false)
    private Integer copyCount = 1;


    @Column(name = "IS_MANDATORY", nullable = false)
    private Boolean isMandatory = true;


    @Column(name = "RULE_STATUS_CODE", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private RecordStatus ruleStatusCode = RecordStatus.ACTIVE;
}
