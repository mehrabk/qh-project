package ir.bank.qh.party.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * رابطه یک شخص حقوقی و صاحب سمت/نماینده در دامنه همان صندوق، بر مبنای عضویت‌های صندوقی.
 * <p>
 * A person's officer/signatory relationship (director, manager, ...) to an
 * organization, scoped to the tenant both memberships belong to.
 */
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "PARTY", name = "ORGANIZATION_OFFICER",
        uniqueConstraints = @UniqueConstraint(name = "UQ_ORG_OFFICER_PERIOD",
                columnNames = {"ORGANIZATION_MEMBERSHIP_ID", "OFFICER_MEMBERSHIP_ID", "OFFICER_ROLE_CODE", "VALID_FROM"}))
@SequenceGenerator(name = "ORGANIZATION_OFFICER_ID_SEQ", schema = "PARTY", sequenceName = "ORGANIZATION_OFFICER_ID_SEQ", allocationSize = 1)
@Getter
@Setter
public class OrganizationOfficerEntity extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORGANIZATION_OFFICER_ID_SEQ")
    @Column(name = "ORGANIZATION_OFFICER_ID")
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ORGANIZATION_MEMBERSHIP_ID", referencedColumnName = "PARTY_MEMBERSHIP_ID", nullable = false,
            foreignKey = @ForeignKey(name = "ORG_OFFICER_FK_ORG_MEMBERSHIP"))
    private PartyMembershipEntity organizationMembership;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "OFFICER_MEMBERSHIP_ID", referencedColumnName = "PARTY_MEMBERSHIP_ID", nullable = false,
            foreignKey = @ForeignKey(name = "ORG_OFFICER_FK_OFFICER_MEMBERSHIP"))
    private PartyMembershipEntity officerMembership;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "OFFICER_ROLE_CODE", referencedColumnName = "OFFICER_ROLE_CODE", nullable = false,
            foreignKey = @ForeignKey(name = "ORG_OFFICER_FK_ROLE"))
    private RefOfficerRoleEntity officerRole;

    @Column(name = "VALID_FROM", nullable = false)
    private Date validFrom;

    @Column(name = "VALID_TO")
    private Date validTo;
}
