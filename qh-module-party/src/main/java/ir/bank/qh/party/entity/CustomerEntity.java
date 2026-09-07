package ir.bank.qh.party.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;

import ir.bank.qh.party.enums.CustomerStatus;
import ir.bank.qh.party.enums.VerificationStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * A Party's status as a bank customer - onboarding/KYC lifecycle and the customer
 * number used across the bank's other systems. Shares its primary key with PARTY
 * (1:1), independent of whether the Party is a PERSON or ORGANIZATION.
 */
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "PARTY", name = "CUSTOMER")
@Getter
@Setter
public class CustomerEntity extends TenantAwareEntity {

    @Id
    @Column(name = "PARTY_ID")
    @EqualsAndHashCode.Include
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "PARTY_ID", foreignKey = @ForeignKey(name = "CUSTOMER_FK_PARTY"))
    private PartyEntity party;

    @Column(name = "CUSTOMER_NO", nullable = false, unique = true, length = 30)
    private String customerNo;

    @Column(name = "CUSTOMER_STATUS_CODE", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private CustomerStatus customerStatus = CustomerStatus.ACTIVE;

    @Column(name = "ONBOARDING_DATE", nullable = false)
    private Date onboardingDate;

    @Column(name = "CLOSURE_DATE")
    private Date closureDate;

    @Column(name = "KYC_STATUS_CODE", length = 30)
    @Enumerated(EnumType.STRING)
    private VerificationStatus kycStatus = VerificationStatus.UNVERIFIED;

    @Column(name = "HOME_BRANCH_CODE", length = 30)
    private String homeBranchCode;
}
