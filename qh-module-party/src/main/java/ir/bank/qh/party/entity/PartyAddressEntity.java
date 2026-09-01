package ir.bank.qh.party.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;

import ir.bank.qh.party.converter.YesNoConverter;
import ir.bank.qh.party.enums.PartyAddressType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Junction between a Party and a (possibly shared) ADDRESS, carrying the usage
 * semantics: what this address means for this specific party (registered, mailing,
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PARTY_ID", referencedColumnName = "PARTY_ID", nullable = false,
            foreignKey = @ForeignKey(name = "PARTY_ADDRESS_FK_PARTY"))
    private PartyEntity party;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ADDRESS_ID", nullable = false,
            foreignKey = @ForeignKey(name = "PARTY_ADDRESS_FK_ADDRESS"))
    private AddressEntity address;

    @Column(name = "ADDRESS_TYPE_CODE", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private PartyAddressType addressType;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "IS_PRIMARY", nullable = false, length = 1, columnDefinition = "char(1) default 'N'")
    private Boolean primary = Boolean.FALSE;

    @Column(name = "VALID_FROM")
    private Date validFrom;

    @Column(name = "VALID_TO")
    private Date validTo;
}
