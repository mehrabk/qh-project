package ir.bank.qh.party.entity;

import ir.bank.qh.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * مشخصات هویتی پایه شخص حقیقی در هسته مرکزی - اطلاعات این جدول بین صندوق‌ها تکرار نمی‌شود.
 * <p>
 * Shares its primary key with PARTY (the model gives PERSON no surrogate id of
 * its own, unlike ORGANIZATION) via {@code @MapsId}.
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GENDER_CODE", referencedColumnName = "GENDER_CODE",
            foreignKey = @ForeignKey(name = "PERSON_FK_GENDER"))
    private RefGenderEntity gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BIRTH_COUNTRY_CODE", referencedColumnName = "COUNTRY_CODE",
            foreignKey = @ForeignKey(name = "PERSON_FK_BIRTH_COUNTRY"))
    private RefCountryEntity birthCountry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BIRTH_PLACE_ID", referencedColumnName = "LOCATION_ID",
            foreignKey = @ForeignKey(name = "PERSON_FK_BIRTH_PLACE"))
    private GeographicLocationEntity birthPlace;

    /** Free-text fallback when the birth place cannot be matched to a GEOGRAPHIC_LOCATION master record. */
    @Column(name = "BIRTH_PLACE_TEXT", length = 200)
    private String birthPlaceText;

    @Column(name = "FATHER_GIVEN_NAME", length = 100)
    private String fatherGivenName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MARITAL_STATUS_CODE", referencedColumnName = "MARITAL_STATUS_CODE",
            foreignKey = @ForeignKey(name = "PERSON_FK_MARITAL_STATUS"))
    private RefMaritalStatusEntity maritalStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESIDENCE_STATUS_CODE", referencedColumnName = "RESIDENCE_STATUS_CODE",
            foreignKey = @ForeignKey(name = "PERSON_FK_RESIDENCE_STATUS"))
    private RefResidenceStatusEntity residenceStatus;

    @Column(name = "PHYSICAL_ABILITY", length = 15)
    private String physicalAbility;
}
