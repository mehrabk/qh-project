package ir.bank.qh.party.entity;

import ir.bank.qh.common.entity.BaseEntity;

import ir.bank.qh.party.enums.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Natural person - identity attributes that are independent of nationality/residence.
 * Shares its primary key with PARTY (1:1, {@code PARTY_ID}), not a JOINED-inheritance
 * subtype - see {@link PartyEntity} for why.
 */
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "PARTY", name = "PERSON")
@Getter
@Setter
public class PersonEntity extends BaseEntity {

    @Id
    @Column(name = "PARTY_ID")
    @EqualsAndHashCode.Include
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "PARTY_ID", foreignKey = @ForeignKey(name = "PERSON_FK_PARTY"))
    private PartyEntity party;

    @Column(name = "BIRTH_DATE", nullable = false)
    private Date birthDate;

    @Column(name = "DEATH_DATE")
    private Date deathDate;

    @Column(name = "GENDER_CODE", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "BIRTH_COUNTRY_CODE", length = 3)
    private String birthCountryCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BIRTH_PLACE_ID", referencedColumnName = "CITY_ID",
            foreignKey = @ForeignKey(name = "PERSON_FK_BIRTH_PLACE"))
    private CityEntity birthPlace;

    /** Free-text fallback when the birth place cannot be matched to a CITY master record. */
    @Column(name = "BIRTH_PLACE_TEXT", length = 200)
    private String birthPlaceText;

    @Column(name = "FATHER_GIVEN_NAME", length = 100)
    private String fatherGivenName;

    @Column(name = "MARITAL_STATUS_CODE", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @Column(name = "RESIDENCE_STATUS_CODE", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private ResidencyStatus residencyStatus;

    @Column(name = "PHYSICAL_ABILITY", length = 15)
    private String physicalAbility;
}
