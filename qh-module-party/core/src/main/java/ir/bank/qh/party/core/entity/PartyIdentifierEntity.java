package ir.bank.qh.party.core.entity;

import ir.bank.qh.party.reference.entity.RefDataSourceEntity;
import ir.bank.qh.party.reference.entity.RefIdentifierTypeEntity;
import ir.bank.qh.party.reference.entity.RefVerificationMethodEntity;
import ir.bank.qh.party.reference.entity.RefVerificationStatusEntity;

import ir.bank.qh.common.entity.BaseEntity;
import ir.bank.qh.party.reference.converter.YesNoConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * شناسه‌های هویتی Party در هسته مرکزی و یکتا - دسترسی صندوق به این جدول مستقیم نیست؛
 * Resolve/Dedup فقط از طریق سرویس مرکزی انجام می‌شود و IDENTIFIER_MATCH_KEY هرگز به کانال
 * صندوق بازگردانده نمی‌شود.
 * <p>
 * A government/authority-issued identifier held by a Party (national ID, passport,
 * tax ID, company registration number, ...), including its independent verification
 * lifecycle - central to KYC/AML compliance. Tenant-independent (no TENANT_ID column).
 */
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "PARTY", name = "PARTY_IDENTIFIER",
        uniqueConstraints = @UniqueConstraint(name = "UQ_PARTY_IDENTIFIER_TYPE_VALUE",
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "IDENTIFIER_TYPE_CODE", referencedColumnName = "IDENTIFIER_TYPE_CODE", nullable = false,
            foreignKey = @ForeignKey(name = "PARTY_IDENTIFIER_FK_TYPE"))
    private RefIdentifierTypeEntity identifierType;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "VERIFICATION_STATUS_CODE", referencedColumnName = "VERIFICATION_STATUS_CODE", nullable = false,
            foreignKey = @ForeignKey(name = "PARTY_IDENTIFIER_FK_VERIF_STATUS"))
    private RefVerificationStatusEntity verificationStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VERIFICATION_SOURCE_CODE", referencedColumnName = "DATA_SOURCE_CODE",
            foreignKey = @ForeignKey(name = "PARTY_IDENTIFIER_FK_VERIF_SOURCE"))
    private RefDataSourceEntity verificationSource;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VERIFICATION_METHOD_CODE", referencedColumnName = "VERIFICATION_METHOD_CODE",
            foreignKey = @ForeignKey(name = "PARTY_IDENTIFIER_FK_VERIF_METHOD"))
    private RefVerificationMethodEntity verificationMethod;

    @Column(name = "VERIFIED_AT")
    private Date verifiedAt;

    @Column(name = "VALID_FROM", nullable = false)
    private Date validFrom;

    @Column(name = "VALID_TO")
    private Date validTo;

    /** Central-only dedup/match key - never surfaced back to a tenant channel by the real service layer. */
    @Column(name = "IDENTIFIER_MATCH_KEY", nullable = false, length = 128)
    private String identifierMatchKey;

    @Column(name = "MATCH_KEY_VERSION", nullable = false, columnDefinition = "number default 1")
    private Integer matchKeyVersion = 1;
}
