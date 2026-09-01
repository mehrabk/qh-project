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
@Table(schema = "LOAN", name = "loan_product_profile")
public class LoanProductProfile extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_version_id", nullable = false, unique = true)
    private ProductVersion productVersion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_type_id", nullable = false)
    private LoanType loanType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_type_id", nullable = false)
    private PlanType planType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_usage_id", nullable = false)
    private LoanUsage loanUsage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "economic_sub_section_id")
    private EconomicSubSection economicSubSection;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operation_parameter_id")
    private FacilityOperationParameter facilityOperationParameter;


    @Column(name = "party_nature_code", length = 30)
    private String partyNatureCode;


    @Column(name = "currency_type_code", length = 3)
    private String currencyTypeCode;
}
