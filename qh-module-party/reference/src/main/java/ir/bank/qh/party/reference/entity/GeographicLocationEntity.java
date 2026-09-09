package ir.bank.qh.party.reference.entity;

import ir.bank.qh.common.entity.BaseEntity;
import ir.bank.qh.party.reference.converter.YesNoConverter;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * موقعیت جغرافیایی مرجع برای محل تولد یا سایر نقاط مکانی.
 * Maps to table GEOGRAPHIC_LOCATION.
 */
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "PARTY", name = "GEOGRAPHIC_LOCATION")
@SequenceGenerator(name = "GEOGRAPHIC_LOCATION_ID_SEQ", schema = "PARTY", sequenceName = "GEOGRAPHIC_LOCATION_ID_SEQ", allocationSize = 1)
@Getter
@Setter
public class GeographicLocationEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEOGRAPHIC_LOCATION_ID_SEQ")
    @Column(name = "LOCATION_ID")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "TITLE_FA", nullable = false, length = 200)
    private String titleFa;

    @Column(name = "DESCRIPTION_FA", length = 500)
    private String descriptionFa;

    @Column(name = "DISPLAY_ORDER", nullable = false, columnDefinition = "number default 0")
    private Integer displayOrder = 0;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "IS_ACTIVE", nullable = false, length = 1, columnDefinition = "char(1) default 'Y'")
    private Boolean active = Boolean.TRUE;

}
