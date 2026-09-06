package ir.bank.qh.party.entity;

import ir.bank.qh.common.entity.BaseEntity;
import ir.bank.qh.common.entity.TenantAwareEntity;

import ir.bank.qh.party.converter.YesNoConverter;
import ir.bank.qh.party.enums.PartyDataSource;
import ir.bank.qh.party.enums.PartyType;
import ir.bank.qh.party.enums.VerificationStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Root of the Party hierarchy - the single BIAN-style "Directory Entry" that every
 * PERSON and ORGANIZATION row joins back to on the shared {@code PARTY_ID} primary
 * key (JOINED inheritance, discriminated by {@code PARTY_TYPE}).
 * <p>
 * Every reusable sub-feature the bank needs regardless of whether the Party is a
 * natural person or an organization - addresses, contact points, identifiers, names,
 * roles, group memberships, signature specimens, compliance inquiries - hangs off
 * this root rather than being duplicated per subtype.
 *
 * @author core-banking-party-model
 */
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "PARTY", name = "PARTY")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "PARTY_TYPE_CODE", discriminatorType = DiscriminatorType.STRING, length = 30)
@SequenceGenerator(name = "PARTY_ID_SEQ", schema = "PARTY", sequenceName = "PARTY_ID_SEQ", allocationSize = 1)
@Getter
@Setter
public abstract class PartyEntity extends BaseEntity {

    @Id
    @Column(name = "PARTY_ID", columnDefinition = "number(19,0)")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PARTY_ID_SEQ")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "VERIFICATION_STATUS_CODE", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private VerificationStatus verificationStatus = VerificationStatus.UNVERIFIED;

    @Column(name = "CREATION_SOURCE_CODE", length = 50)
    @Enumerated(EnumType.STRING)
    private PartyDataSource dataSource;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "IS_CURRENT", nullable = false, length = 1, columnDefinition = "char(1) default 'Y'")
    private Boolean current = Boolean.TRUE;

    @JsonIgnoreProperties("party")
    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PartyAddressEntity> addresses = new ArrayList<>();

    @JsonIgnoreProperties("party")
    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContactPointEntity> contactPoints = new ArrayList<>();

    @JsonIgnoreProperties("party")
    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PartyIdentifierEntity> identifiers = new ArrayList<>();

    @JsonIgnoreProperties("party")
    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PartyNameEntity> names = new ArrayList<>();

    @JsonIgnoreProperties("party")
    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PartyRoleEntity> roles = new ArrayList<>();

    @JsonIgnoreProperties("party")
    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PartyGroupMemberEntity> groupMemberships = new ArrayList<>();

    @JsonIgnoreProperties("party")
    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SignatureSpecimenEntity> signatureSpecimens = new ArrayList<>();

    @JsonIgnoreProperties("party")
    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PartyInquiryEntity> inquiries = new ArrayList<>();

    @JsonIgnoreProperties("party")
    @OneToOne(mappedBy = "party", cascade = CascadeType.ALL, orphanRemoval = true)
    private PartyDemographicEntity demographic;

    /** Backed by the {@code PARTY_TYPE_CODE} discriminator itself - never a separately stored column. */
    @Transient
    public abstract PartyType getPartyType();

    public void addAddress(PartyAddressEntity address) {
        address.setParty(this);
        this.addresses.add(address);
    }

    public void addContactPoint(ContactPointEntity contactPoint) {
        contactPoint.setParty(this);
        this.contactPoints.add(contactPoint);
    }

    public void addIdentifier(PartyIdentifierEntity identifier) {
        identifier.setParty(this);
        this.identifiers.add(identifier);
    }

    public void addName(PartyNameEntity name) {
        name.setParty(this);
        this.names.add(name);
    }

    public void addRole(PartyRoleEntity role) {
        role.setParty(this);
        this.roles.add(role);
    }

    public void addSignatureSpecimen(SignatureSpecimenEntity specimen) {
        specimen.setParty(this);
        this.signatureSpecimens.add(specimen);
    }

    public void addInquiry(PartyInquiryEntity inquiry) {
        inquiry.setParty(this);
        this.inquiries.add(inquiry);
    }

    public void linkDemographic(PartyDemographicEntity demographic) {
        demographic.setParty(this);
        this.demographic = demographic;
    }
}
