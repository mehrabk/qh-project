package ir.bank.qh.party.core.entity;

import ir.bank.qh.common.entity.BaseEntity;
import ir.bank.qh.party.reference.converter.YesNoConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * نام و نام خانوادگی/نام ثبتی پارتی در هسته مرکزی - این اطلاعات مستقل از صندوق نگهداری می‌شود.
 * <p>
 * Kept as its own entity - rather than plain fields on PERSON/ORGANIZATION - so a
 * Party can carry several names over time (legal name, alias, previous registered
 * name), each independently time-boxed. Tenant-independent (no TENANT_ID column).
 */
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "PARTY", name = "PARTY_NAME")
@SequenceGenerator(name = "PARTY_NAME_ID_SEQ", schema = "PARTY", sequenceName = "PARTY_NAME_ID_SEQ", allocationSize = 1)
@Getter
@Setter
public class PartyNameEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PARTY_NAME_ID_SEQ")
    @Column(name = "PARTY_NAME_ID")
    @EqualsAndHashCode.Include
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PARTY_ID", referencedColumnName = "PARTY_ID", nullable = false,
            foreignKey = @ForeignKey(name = "PARTY_NAME_FK_PARTY"))
    private PartyEntity party;

    @Column(name = "GIVEN_NAME", length = 150)
    private String givenName;

    @Column(name = "FAMILY_NAME", length = 150)
    private String familyName;

    /** Full display name - required for organizations, derived/optional for persons. */
    @Column(name = "FULL_NAME", nullable = false, length = 500)
    private String fullName;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "IS_PRIMARY", nullable = false, length = 1, columnDefinition = "char(1) default 'N'")
    private Boolean primary = Boolean.FALSE;

    @Column(name = "VALID_FROM", nullable = false)
    private Date validFrom;

    @Column(name = "VALID_TO")
    private Date validTo;
}
