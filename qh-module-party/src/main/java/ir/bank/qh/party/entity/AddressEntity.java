package ir.bank.qh.party.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Normalized address master data (matches the diagram's ADDRESS entity), intentionally
 * kept independent of any single Party so the same physical address can be shared and
 * referenced from several parties (e.g. co-residents, a company and its registered
 * agent). The Party-to-Address link with its usage semantics lives in
 * {@link PartyAddressEntity}.
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

    @Column(name = "COUNTRY_CODE", length = 3)
    private String countryCode;

    @Column(name = "PROVINCE_CODE", length = 30)
    private String provinceCode;

    @Column(name = "CITY_CODE", length = 20)
    private String cityCode;

    @Column(name = "COUNTY_CODE", length = 20)
    private String countyCode;

    @Column(name = "DISTRICT_CODE", length = 30)
    private String districtCode;

    @Column(name = "NEIGHBORHOOD_TEXT", length = 200)
    private String neighborhoodText;

    @Column(name = "MAIN_STREET_TEXT", length = 200)
    private String mainStreetText;

    @Column(name = "SIDE_STREET_TEXT", length = 200)
    private String sideStreetText;

    @Column(name = "PLAQUE_NO", length = 20)
    private String plaqueNo;

    @Column(name = "FLOOR_NO", length = 20)
    private String floorNo;

    @Column(name = "UNIT_NO", length = 20)
    private String unitNo;

    @Column(name = "POSTAL_CODE", length = 20)
    private String postalCode;

    @Column(name = "ADDRESS_LINE_1", length = 500)
    private String addressLine1;

    @Column(name = "ADDRESS_LINE_2", length = 500)
    private String addressLine2;

    @Column(name = "ADDRESS_DETAIL", length = 500)
    private String addressDetail;
}
