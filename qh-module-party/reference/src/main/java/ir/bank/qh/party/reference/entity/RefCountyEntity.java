package ir.bank.qh.party.reference.entity;

import ir.bank.qh.common.entity.BaseEntity;
import ir.bank.qh.party.reference.converter.YesNoConverter;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * شهرستان‌های مورد استفاده در نشانی.
 * Maps to table REF_COUNTY.
 */
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "PARTY", name = "REF_COUNTY")
@Getter
@Setter
public class RefCountyEntity extends BaseEntity {

    // Named "id" (not "code") so JpaReferenceResolver - which looks up a field literally
    // named "id" to resolve a client-supplied {"id": ...} reference - works unmodified for
    // this natural-key (String) PK the same way it already does for every Long-id entity.
    @Id
    @Column(name = "COUNTY_CODE", length = 50)
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
            foreignKey = @ForeignKey(name = "REF_COUNTY_FK_PROVINCE"))
    private RefProvinceEntity province;

}
