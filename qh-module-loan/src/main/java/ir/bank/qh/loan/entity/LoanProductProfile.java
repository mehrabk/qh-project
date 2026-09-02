package ir.bank.qh.loan.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Specialised loan identity for a product version: links to loan type, plan type, usage, economic sub-section.
 * Maps to table LOAN_PRODUCT_PROFILE.
 */
@Getter
@Setter
@Entity
@Table(schema = "LOAN", name = "loan_product_profile")
public class LoanProductProfile extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @Column(name = "PRODUCT_VERSION_ID", nullable = false, unique = true)
    private Long productVersionId;

    @Column(name = "LOAN_TYPE_ID", nullable = false)
    private Long loanTypeId;

    @Column(name = "PLAN_TYPE_ID", nullable = false)
    private Long planTypeId;

    @Column(name = "LOAN_USAGE_ID", nullable = false)
    private Long loanUsageId;

    @Column(name = "ECONOMIC_SUB_SECTION_ID")
    private Long economicSubSectionId;

    @Column(name = "OPERATION_PARAMETER_ID")
    private Long facilityOperationParameterId;


    @Column(name = "PARTY_NATURE_CODE", length = 30)
    private String partyNatureCode;


    @Column(name = "CURRENCY_TYPE_CODE", length = 3)
    private String currencyTypeCode;
}
