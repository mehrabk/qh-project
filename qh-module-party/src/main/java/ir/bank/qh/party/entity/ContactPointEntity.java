package ir.bank.qh.party.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;

import ir.bank.qh.party.converter.YesNoConverter;
import ir.bank.qh.party.enums.ContactPurpose;
import ir.bank.qh.party.enums.ContactType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/** A single communication channel (phone, email, fax, website, ...) owned by a Party. */
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "PARTY", name = "CONTACT_POINT")
@SequenceGenerator(name = "CONTACT_POINT_ID_SEQ", schema = "PARTY", sequenceName = "CONTACT_POINT_ID_SEQ", allocationSize = 1)
@Getter
@Setter
public class ContactPointEntity extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTACT_POINT_ID_SEQ")
    @Column(name = "CONTACT_POINT_ID")
    @EqualsAndHashCode.Include
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PARTY_ID", referencedColumnName = "PARTY_ID", nullable = false,
            foreignKey = @ForeignKey(name = "CONTACT_POINT_FK_PARTY"))
    private PartyEntity party;

    @Column(name = "CONTACT_TYPE_CODE", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private ContactType contactType;

    @Column(name = "CONTACT_VALUE", nullable = false, length = 500)
    private String contactValue;

    @Column(name = "PURPOSE_CODE", length = 30)
    @Enumerated(EnumType.STRING)
    private ContactPurpose purpose;

    @Column(name = "AREA_CODE", length = 10)
    private String areaCode;

    @Column(name = "EXTENSION_NO", length = 10)
    private String extensionNo;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "IS_PRIMARY", nullable = false, length = 1, columnDefinition = "char(1) default 'N'")
    private Boolean primary = Boolean.FALSE;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "IS_VERIFIED", nullable = false, length = 1, columnDefinition = "char(1) default 'N'")
    private Boolean verified = Boolean.FALSE;

    @Column(name = "VERIFIED_AT")
    private java.util.Date verifiedAt;

    @Column(name = "VALID_FROM")
    private java.util.Date validFrom;

    @Column(name = "VALID_TO")
    private java.util.Date validTo;
}
