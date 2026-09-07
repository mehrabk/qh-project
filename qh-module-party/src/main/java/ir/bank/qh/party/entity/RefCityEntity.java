package ir.bank.qh.party.entity;

import ir.bank.qh.common.entity.BaseEntity;
import ir.bank.qh.party.converter.YesNoConverter;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * شهرهای مورد استفاده در نشانی و محل ثبت.
 * Maps to table REF_CITY.
 */
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "PARTY", name = "REF_CITY")
@Getter
@Setter
public class RefCityEntity extends BaseEntity {

    // Named "id" (not "code") so JpaReferenceResolver - which looks up a field literally
    // named "id" to resolve a client-supplied {"id": ...} reference - works unmodified for
    // this natural-key (String) PK the same way it already does for every Long-id entity.
    @Id
    @Column(name = "CITY_CODE", length = 50)
    @EqualsAndHashCode.Include
    private String id;

    @Column(name = "TITLE_FA", nullable = false, length = 200)
    private String titleFa;

    @Column(name = "DESCRIPTION_FA", length = 500)
    private String descriptionFa;

    @Column(name = "DISPLAY_ORDER", nullable = false, columnDefinition = "number default 0")
    private Integer displayOrder = 0;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "IS_ACTIVE", nullable = false, length = 1, columnDefinition = "char(1) default 'Y'")
    private Boolean active = Boolean.TRUE;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PROVINCE_CODE", referencedColumnName = "PROVINCE_CODE", nullable = false,
            foreignKey = @ForeignKey(name = "REF_CITY_FK_PROVINCE"))
    private RefProvinceEntity province;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COUNTY_CODE", referencedColumnName = "COUNTY_CODE",
            foreignKey = @ForeignKey(name = "REF_CITY_FK_COUNTY"))
    private RefCountyEntity county;

}
