package ir.bank.qh.party.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * A natural person acting as an officer/signatory (director, manager, ...) of an
 * organization - e.g. "Party X is MANAGING_DIRECTOR of Organization Y".
 */
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "PARTY", name = "ORGANIZATION_OFFICER",
        uniqueConstraints = @UniqueConstraint(name = "UQ_ORG_OFFICER_PERIOD",
                columnNames = {"ORGANIZATION_PARTY_ID", "OFFICER_PARTY_ID", "OFFICER_ROLE_CODE", "VALID_FROM"}))
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
    @JoinColumn(name = "ORGANIZATION_PARTY_ID", referencedColumnName = "PARTY_ID", nullable = false,
            foreignKey = @ForeignKey(name = "FK_ORGANIZATION_OFFICER_ORGANIZATION_PARTY"))
    private PartyEntity organizationParty;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "OFFICER_PARTY_ID", referencedColumnName = "PARTY_ID", nullable = false,
            foreignKey = @ForeignKey(name = "FK_ORGANIZATION_OFFICER_OFFICER_PARTY"))
    private PartyEntity officerParty;

    @Column(name = "OFFICER_ROLE_CODE", nullable = false, length = 40)
    private String officerRoleCode;

    @Column(name = "VALID_FROM")
    private Date validFrom;

    @Column(name = "VALID_TO")
    private Date validTo;
}
