package ir.bank.qh.party.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;

import ir.bank.qh.party.converter.YesNoConverter;
import ir.bank.qh.party.enums.ContactPointAddressAssociationType;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Associates a CONTACT_POINT (e.g. a landline number) with the PARTY_ADDRESS it is
 * physically located at - useful for channels tied to a place, such as a work phone
 * tied to a business address.
 */
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "PARTY", name = "CONTACT_POINT_ADDRESS",
        uniqueConstraints = @UniqueConstraint(name = "UQ_CPA_CONTACT_ADDRESS",
                columnNames = {"CONTACT_POINT_ID", "PARTY_ADDRESS_ID"}))
@SequenceGenerator(name = "CONTACT_POINT_ADDRESS_ID_SEQ", schema = "PARTY", sequenceName = "CONTACT_POINT_ADDRESS_ID_SEQ", allocationSize = 1)
@Getter
@Setter
public class ContactPointAddressEntity extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTACT_POINT_ADDRESS_ID_SEQ")
    @Column(name = "CONTACT_POINT_ADDRESS_ID")
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CONTACT_POINT_ID", referencedColumnName = "CONTACT_POINT_ID", nullable = false,
            foreignKey = @ForeignKey(name = "CPA_FK_CONTACT_POINT"))
    private ContactPointEntity contactPoint;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PARTY_ADDRESS_ID", referencedColumnName = "PARTY_ADDRESS_ID", nullable = false,
            foreignKey = @ForeignKey(name = "CPA_FK_PARTY_ADDRESS"))
    private PartyAddressEntity partyAddress;

    @Column(name = "ASSOCIATION_TYPE_CODE", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private ContactPointAddressAssociationType associationType = ContactPointAddressAssociationType.LOCATED_AT;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "IS_PRIMARY_FOR_ADDRESS", nullable = false, length = 1, columnDefinition = "char(1) default 'N'")
    private Boolean primaryForAddress = Boolean.FALSE;
}
