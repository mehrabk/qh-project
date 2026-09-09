package ir.bank.qh.party.core.entity;

import ir.bank.qh.party.reference.entity.RefAddressTypeEntity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import ir.bank.qh.party.reference.converter.YesNoConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * رابطه عضویت پارتی در صندوق با نشانی، نوع نشانی، اصلی بودن و دوره اعتبار.
 * <p>
 * Junction between a PARTY_MEMBERSHIP and a (possibly shared) ADDRESS, carrying the
 * usage semantics: what this address means in this tenant (registered, mailing,
 * residential, ...), whether it is the primary one, and for how long it is valid.
 */
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "PARTY", name = "PARTY_ADDRESS")
@SequenceGenerator(name = "PARTY_ADDRESS_ID_SEQ", schema = "PARTY", sequenceName = "PARTY_ADDRESS_ID_SEQ", allocationSize = 1)
@Getter
@Setter
public class PartyAddressEntity extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PARTY_ADDRESS_ID_SEQ")
    @Column(name = "PARTY_ADDRESS_ID")
    @EqualsAndHashCode.Include
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PARTY_MEMBERSHIP_ID", referencedColumnName = "PARTY_MEMBERSHIP_ID", nullable = false,
            foreignKey = @ForeignKey(name = "PARTY_ADDRESS_FK_MEMBERSHIP"))
    private PartyMembershipEntity partyMembership;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ADDRESS_ID", nullable = false,
            foreignKey = @ForeignKey(name = "PARTY_ADDRESS_FK_ADDRESS"))
    private AddressEntity address;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ADDRESS_TYPE_CODE", referencedColumnName = "ADDRESS_TYPE_CODE", nullable = false,
            foreignKey = @ForeignKey(name = "PARTY_ADDRESS_FK_TYPE"))
    private RefAddressTypeEntity addressType;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "IS_PRIMARY", nullable = false, length = 1, columnDefinition = "char(1) default 'N'")
    private Boolean primary = Boolean.FALSE;

    @Column(name = "VALID_FROM", nullable = false)
    private Date validFrom;

    @Column(name = "VALID_TO")
    private Date validTo;
}
