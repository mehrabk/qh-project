package ir.bank.qh.party.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;

import ir.bank.qh.party.converter.YesNoConverter;
import ir.bank.qh.party.enums.NameType;
import ir.bank.qh.party.enums.VerificationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * A name held by a Party. Kept as its own entity - rather than plain fields on
 * PERSON/ORGANIZATION - so a Party can carry several names over time (legal name,
 * alias, maiden name, trade name, previous registered name), each independently
 * verifiable and time-boxed.
 */
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "PARTY", name = "PARTY_NAME")
@SequenceGenerator(name = "PARTY_NAME_ID_SEQ", schema = "PARTY", sequenceName = "PARTY_NAME_ID_SEQ", allocationSize = 1)
@Getter
@Setter
public class PartyNameEntity extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PARTY_NAME_ID_SEQ")
    @Column(name = "PARTY_NAME_ID")
    @EqualsAndHashCode.Include
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PARTY_ID", referencedColumnName = "PARTY_ID", nullable = false,
            foreignKey = @ForeignKey(name = "PARTY_NAME_FK_PARTY"))
    private PartyEntity party;

    @Column(name = "NAME_TYPE_CODE", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private NameType nameType;

    @Column(name = "GIVEN_NAME", length = 150)
    private String givenName;

    @Column(name = "FAMILY_NAME", length = 150)
    private String familyName;

    /** Full display name - required for organizations, derived/optional for persons. */
    @Column(name = "FULL_NAME", nullable = false, length = 500)
    private String fullName;

    @Column(name = "LANGUAGE_CODE", length = 10)
    private String languageCode;

    @Column(name = "SCRIPT_CODE", length = 20)
    private String scriptCode;

    @Column(name = "VERIFICATION_STATUS_CODE", length = 30)
    @Enumerated(EnumType.STRING)
    private VerificationStatus verificationStatus = VerificationStatus.UNVERIFIED;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "IS_PRIMARY", nullable = false, length = 1, columnDefinition = "char(1) default 'N'")
    private Boolean primary = Boolean.FALSE;

    @Column(name = "VALID_FROM")
    private Date validFrom;

    @Column(name = "VALID_TO")
    private Date validTo;
}
