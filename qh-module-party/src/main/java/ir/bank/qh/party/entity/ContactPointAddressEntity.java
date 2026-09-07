package ir.bank.qh.party.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.party.converter.YesNoConverter;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/** ارتباط راه تماس و نشانی برای همان PARTY_MEMBERSHIP و همان صندوق. */
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
    @JoinColumn(name = "PARTY_MEMBERSHIP_ID", referencedColumnName = "PARTY_MEMBERSHIP_ID", nullable = false,
            foreignKey = @ForeignKey(name = "CPA_FK_MEMBERSHIP"))
    private PartyMembershipEntity partyMembership;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CONTACT_POINT_ID", referencedColumnName = "CONTACT_POINT_ID", nullable = false,
            foreignKey = @ForeignKey(name = "CPA_FK_CONTACT_POINT"))
    private ContactPointEntity contactPoint;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PARTY_ADDRESS_ID", referencedColumnName = "PARTY_ADDRESS_ID", nullable = false,
            foreignKey = @ForeignKey(name = "CPA_FK_PARTY_ADDRESS"))
    private PartyAddressEntity partyAddress;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ASSOCIATION_TYPE_CODE", referencedColumnName = "ASSOCIATION_TYPE_CODE", nullable = false,
            foreignKey = @ForeignKey(name = "CPA_FK_ASSOC_TYPE"))
    private RefContactAddressAssocTypeEntity associationType;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "IS_PRIMARY_FOR_ADDRESS", nullable = false, length = 1, columnDefinition = "char(1) default 'N'")
    private Boolean primaryForAddress = Boolean.FALSE;

    @Column(name = "VALID_FROM", nullable = false)
    private Date validFrom;

    @Column(name = "VALID_TO")
    private Date validTo;
}
