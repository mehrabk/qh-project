package ir.bank.qh.party.core.entity;

import ir.bank.qh.party.reference.entity.RefCustomerStatusEntity;
import ir.bank.qh.party.reference.entity.RefKycStatusEntity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * اطلاعات مشتری بودن یک پارتی در یک صندوق مشخص - CUSTOMER به PARTY_MEMBERSHIP وابسته است
 * و مستقل از هویت مرکزی Party نگهداری می‌شود.
 */
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "PARTY", name = "CUSTOMER")
@SequenceGenerator(name = "CUSTOMER_ID_SEQ", schema = "PARTY", sequenceName = "CUSTOMER_ID_SEQ", allocationSize = 1)
@Getter
@Setter
public class CustomerEntity extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUSTOMER_ID_SEQ")
    @Column(name = "CUSTOMER_ID")
    @EqualsAndHashCode.Include
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PARTY_MEMBERSHIP_ID", referencedColumnName = "PARTY_MEMBERSHIP_ID", nullable = false,
            foreignKey = @ForeignKey(name = "CUSTOMER_FK_MEMBERSHIP"))
    private PartyMembershipEntity partyMembership;

    @Column(name = "CUSTOMER_NO", nullable = false, unique = true, length = 30)
    private String customerNo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CUSTOMER_STATUS_CODE", referencedColumnName = "CUSTOMER_STATUS_CODE", nullable = false,
            foreignKey = @ForeignKey(name = "CUSTOMER_FK_STATUS"))
    private RefCustomerStatusEntity customerStatus;

    @Column(name = "ONBOARDING_DATE", nullable = false)
    private Date onboardingDate;

    @Column(name = "CLOSURE_DATE")
    private Date closureDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "KYC_STATUS_CODE", referencedColumnName = "KYC_STATUS_CODE", nullable = false,
            foreignKey = @ForeignKey(name = "CUSTOMER_FK_KYC_STATUS"))
    private RefKycStatusEntity kycStatus;

    @Column(name = "HOME_BRANCH_CODE", length = 30)
    private String homeBranchCode;
}
