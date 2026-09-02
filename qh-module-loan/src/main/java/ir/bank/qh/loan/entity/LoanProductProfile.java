package ir.bank.qh.loan.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.core.entity.ProductVersion;
import ir.bank.qh.reference.entity.EconomicSubSection;
import ir.bank.qh.reference.entity.FacilityOperationParameter;
import ir.bank.qh.reference.entity.LoanType;
import ir.bank.qh.reference.entity.LoanUsage;
import ir.bank.qh.reference.entity.PlanType;
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
@Table(schema = "PRODUCTBUILDER", name = "loan_product_profile")
public class LoanProductProfile extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_VERSION_ID", nullable = false, unique = true)
    private ProductVersion productVersion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOAN_TYPE_ID", nullable = false)
    private LoanType loanType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAN_TYPE_ID", nullable = false)
    private PlanType planType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOAN_USAGE_ID", nullable = false)
    private LoanUsage loanUsage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ECONOMIC_SUB_SECTION_ID")
    private EconomicSubSection economicSubSection;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OPERATION_PARAMETER_ID")
    private FacilityOperationParameter facilityOperationParameter;


    @Column(name = "PARTY_NATURE_CODE", length = 30)
    private String partyNatureCode;


    @Column(name = "CURRENCY_TYPE_CODE", length = 3)
    private String currencyTypeCode;
}
