package ir.bank.qh.party.entity;

import ir.bank.qh.common.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Country reference/master data - shared across every institution (not tenant-scoped).
 * Used for nationality, birth country, registration country and address country.
 */
@Entity
@Table(schema = "PARTY", name = "COUNTRY")
@SequenceGenerator(name = "COUNTRY_ID_SEQ", schema = "PARTY", sequenceName = "COUNTRY_ID_SEQ", allocationSize = 1)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Getter
@Setter
public class CountryEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COUNTRY_ID_SEQ")
    @Column(name = "COUNTRY_ID")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "NAME", nullable = false, length = 200)
    private String name;

    @Column(name = "ISO_CODE", nullable = false, length = 3, unique = true)
    private String isoCode;
}
