package ir.bank.qh.party.entity;

import ir.bank.qh.party.enums.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Natural person - identity attributes that are independent of nationality/residence
 * (which live on {@link PartyEntity#getPartyType()} via {@link ResidencyStatus}).
 */
@Entity
@Table(schema = "PARTY", name = "PERSON")
@PrimaryKeyJoinColumn(name = "PARTY_ID", foreignKey = @ForeignKey(name = "PERSON_FK_PARTY"))
@DiscriminatorValue("PERSON")
@Getter
@Setter
public class PersonEntity extends PartyEntity {

    @Column(name = "BIRTH_DATE", nullable = false)
    private Date birthDate;

    @Column(name = "DEATH_DATE")
    private Date deathDate;

    @Column(name = "GENDER_CODE", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BIRTH_COUNTRY_ID", referencedColumnName = "COUNTRY_ID",
            foreignKey = @ForeignKey(name = "PERSON_FK_BIRTH_COUNTRY"))
    private CountryEntity birthCountry;

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

    @Column(name = "PHYSICAL_ABILITY_CODE", length = 30)
    @Enumerated(EnumType.STRING)
    private PhysicalAbilityStatus physicalAbilityStatus;

    @Column(name = "DATA_QUALITY_STATUS_CODE", length = 30)
    @Enumerated(EnumType.STRING)
    private DataQualityStatus dataQualityStatus;

    @Column(name = "LEGAL_CAPACITY_CODE", length = 30)
    @Enumerated(EnumType.STRING)
    private LegalCapacityStatus legalCapacityStatus;

    @Column(name = "LIFE_STATUS_CODE", length = 30)
    @Enumerated(EnumType.STRING)
    private LifeStatus lifeStatus;

    /** ISO 639-1 preferred-language code, e.g. {@code fa}, {@code en}. */
    @Column(name = "LANGUAGE_CODE", length = 10)
    private String languageCode;

    @Override
    public PartyType getPartyType() {
        return PartyType.PERSON;
    }
}
