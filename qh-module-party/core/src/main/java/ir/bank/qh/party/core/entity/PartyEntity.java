package ir.bank.qh.party.core.entity;

import ir.bank.qh.party.reference.entity.RefDataSourceEntity;
import ir.bank.qh.party.reference.entity.RefPartyTypeEntity;
import ir.bank.qh.party.reference.entity.RefVerificationStatusEntity;

import ir.bank.qh.common.entity.BaseEntity;
import ir.bank.qh.party.reference.converter.YesNoConverter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * هسته مرکزی و یکتای هویت پارتی در کل سامانه - هر شخص حقیقی یا حقوقی فقط یک PARTY_ID دارد و
 * این رکورد به صندوق خاصی وابسته نیست.
 * <p>
 * The single central, tenant-independent identity root every PERSON/ORGANIZATION
 * detail row and every PARTY_MEMBERSHIP (per-tenant boundary) links back to via
 * PARTY_ID. Unlike the previous diagram, PERSON/ORGANIZATION are modeled here as
 * plain FK-linked detail tables rather than a JPA JOINED-inheritance hierarchy -
 * the new model gives ORGANIZATION its own surrogate ORGANIZATION_ID (distinct
 * from PARTY_ID), which a shared-primary-key inheritance mapping cannot express,
 * so {@code partyType} is a real FK to REF_PARTY_TYPE instead of a Hibernate
 * discriminator column.
 */
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "PARTY", name = "PARTY")
@SequenceGenerator(name = "PARTY_ID_SEQ", schema = "PARTY", sequenceName = "PARTY_ID_SEQ", allocationSize = 1)
@Getter
@Setter
public class PartyEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PARTY_ID_SEQ")
    @Column(name = "PARTY_ID")
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PARTY_TYPE_CODE", referencedColumnName = "PARTY_TYPE_CODE", nullable = false,
            foreignKey = @ForeignKey(name = "PARTY_FK_PARTY_TYPE"))
    private RefPartyTypeEntity partyType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "VERIFICATION_STATUS_CODE", referencedColumnName = "VERIFICATION_STATUS_CODE", nullable = false,
            foreignKey = @ForeignKey(name = "PARTY_FK_VERIFICATION_STATUS"))
    private RefVerificationStatusEntity verificationStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATION_SOURCE_CODE", referencedColumnName = "DATA_SOURCE_CODE",
            foreignKey = @ForeignKey(name = "PARTY_FK_DATA_SOURCE"))
    private RefDataSourceEntity dataSource;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "IS_CURRENT", nullable = false, length = 1, columnDefinition = "char(1) default 'Y'")
    private Boolean current = Boolean.TRUE;

    @JsonIgnoreProperties("party")
    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PartyNameEntity> names = new ArrayList<>();

    @JsonIgnoreProperties("party")
    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PartyIdentifierEntity> identifiers = new ArrayList<>();

    @JsonIgnoreProperties("party")
    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PartyMembershipEntity> memberships = new ArrayList<>();

    public void addName(PartyNameEntity name) {
        name.setParty(this);
        this.names.add(name);
    }

    public void addIdentifier(PartyIdentifierEntity identifier) {
        identifier.setParty(this);
        this.identifiers.add(identifier);
    }

    public void addMembership(PartyMembershipEntity membership) {
        membership.setParty(this);
        this.memberships.add(membership);
    }
}
