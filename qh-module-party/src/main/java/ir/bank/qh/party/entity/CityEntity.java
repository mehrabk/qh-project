package ir.bank.qh.party.entity;

import ir.bank.qh.common.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * City / place reference data - shared across every institution. Used for birth
 * place and as a component of ADDRESS.
 */
@Entity
@Table(schema = "PARTY", name = "CITY")
@SequenceGenerator(name = "CITY_ID_SEQ", schema = "PARTY", sequenceName = "CITY_ID_SEQ", allocationSize = 1)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Getter
@Setter
public class CityEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CITY_ID_SEQ")
    @Column(name = "CITY_ID")
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "COUNTRY_ID", referencedColumnName = "COUNTRY_ID", nullable = false,
            foreignKey = @ForeignKey(name = "CITY_FK_COUNTRY"))
    private CountryEntity country;

    @Column(name = "PROVINCE_NAME", length = 200)
    private String provinceName;

    @Column(name = "NAME", nullable = false, length = 200)
    private String name;
}
