package ir.bank.qh.party.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * جزئیات نشانی ثبت‌شده در دامنه یک صندوق - یک Party می‌تواند در صندوق‌های مختلف نشانی‌های
 * متفاوت داشته باشد.
 * <p>
 * Normalized address data, independent of any single Party so the same physical
 * address can be shared and referenced from several parties. The Party-to-Address
 * link with its usage semantics lives in {@link PartyAddressEntity}.
 */
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "PARTY", name = "ADDRESS")
@SequenceGenerator(name = "ADDRESS_ID_SEQ", schema = "PARTY", sequenceName = "ADDRESS_ID_SEQ", allocationSize = 1)
@Getter
@Setter
public class AddressEntity extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADDRESS_ID_SEQ")
    @Column(name = "ADDRESS_ID")
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "COUNTRY_CODE", referencedColumnName = "COUNTRY_CODE", nullable = false,
            foreignKey = @ForeignKey(name = "ADDRESS_FK_COUNTRY"))
    private RefCountryEntity country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROVINCE_CODE", referencedColumnName = "PROVINCE_CODE",
            foreignKey = @ForeignKey(name = "ADDRESS_FK_PROVINCE"))
    private RefProvinceEntity province;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CITY_CODE", referencedColumnName = "CITY_CODE",
            foreignKey = @ForeignKey(name = "ADDRESS_FK_CITY"))
    private RefCityEntity city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COUNTY_CODE", referencedColumnName = "COUNTY_CODE",
            foreignKey = @ForeignKey(name = "ADDRESS_FK_COUNTY"))
    private RefCountyEntity county;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DISTRICT_CODE", referencedColumnName = "DISTRICT_CODE",
            foreignKey = @ForeignKey(name = "ADDRESS_FK_DISTRICT"))
    private RefDistrictEntity district;

    @Column(name = "POSTAL_CODE", length = 20)
    private String postalCode;

    @Column(name = "ADDRESS_LINE1", nullable = false, length = 500)
    private String addressLine1;

    @Column(name = "ADDRESS_LINE2", length = 500)
    private String addressLine2;

    @Column(name = "NEIGHBORHOOD_TEXT", length = 100)
    private String neighborhoodText;

    @Column(name = "MAIN_STREET_TEXT", length = 200)
    private String mainStreetText;

    @Column(name = "SIDE_STREET_TEXT", length = 200)
    private String sideStreetText;

    @Column(name = "PLAQUE_NO", length = 30)
    private String plaqueNo;

    @Column(name = "FLOOR_NO", length = 20)
    private String floorNo;

    @Column(name = "UNIT_NO", length = 20)
    private String unitNo;

    @Column(name = "ADDRESS_DETAIL", length = 500)
    private String addressDetail;
}
