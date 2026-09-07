package ir.bank.qh.party.entity;

import ir.bank.qh.common.entity.BaseEntity;

import ir.bank.qh.party.converter.YesNoConverter;
import ir.bank.qh.party.enums.IdentifierType;
import ir.bank.qh.party.enums.VerificationMethod;
import ir.bank.qh.party.enums.VerificationStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * A government/authority-issued identifier held by a Party (national ID, passport,
 * tax ID, company registration number, ...), including its independent verification
 * lifecycle - central to KYC/AML compliance. Tenant-independent: the same identifier
 * (e.g. national ID) identifies the Party regardless of which institution looks it up.
 */
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "PARTY", name = "PARTY_IDENTIFIER",
        uniqueConstraints = @UniqueConstraint(name = "UQ_IDENTIFIER_TYPE_VALUE",
                columnNames = {"IDENTIFIER_TYPE_CODE", "IDENTIFIER_VALUE"}))
@SequenceGenerator(name = "PARTY_IDENTIFIER_ID_SEQ", schema = "PARTY", sequenceName = "PARTY_IDENTIFIER_ID_SEQ", allocationSize = 1)
@Getter
@Setter
public class PartyIdentifierEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PARTY_IDENTIFIER_ID_SEQ")
    @Column(name = "PARTY_IDENTIFIER_ID")
    @EqualsAndHashCode.Include
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PARTY_ID", referencedColumnName = "PARTY_ID", nullable = false,
            foreignKey = @ForeignKey(name = "PARTY_IDENTIFIER_FK_PARTY"))
    private PartyEntity party;

    @Column(name = "IDENTIFIER_TYPE_CODE", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private IdentifierType identifierType;

    @Column(name = "IDENTIFIER_VALUE", nullable = false, length = 200)
    private String identifierValue;

    @Column(name = "ISSUE_DATE")
    private Date issueDate;

    @Column(name = "EXPIRY_DATE")
    private Date expiryDate;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "IS_PRIMARY", nullable = false, length = 1, columnDefinition = "char(1) default 'N'")
    private Boolean primary = Boolean.FALSE;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "IS_ACTIVE", nullable = false, length = 1, columnDefinition = "char(1) default 'Y'")
    private Boolean active = Boolean.TRUE;

    @Column(name = "VERIFICATION_STATUS_CODE", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private VerificationStatus verificationStatus = VerificationStatus.UNVERIFIED;

    @Column(name = "VERIFICATION_METHOD_CODE", length = 30)
    @Enumerated(EnumType.STRING)
    private VerificationMethod verificationMethod;

    @Column(name = "VERIFICATION_SOURCE_CODE", length = 30)
    private String verificationSource;

    @Column(name = "VERIFIED_AT")
    private Date verifiedAt;

    /** Normalized match key (e.g. digits-only, uppercased) used for fuzzy/duplicate matching across identifiers. */
    @Column(name = "IDENTIFIER_MATCH_KEY", length = 128)
    private String identifierMatchKey;

    /** Version of the match-key normalization algorithm that produced {@link #identifierMatchKey}. */
    @Column(name = "MATCH_KEY_VERSION")
    private Integer matchKeyVersion = 1;

    @Column(name = "VALID_FROM")
    private Date validFrom;

    @Column(name = "VALID_TO")
    private Date validTo;
}
