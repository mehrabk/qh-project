package ir.bank.qh.party.entity;

import ir.bank.qh.common.entity.BaseEntity;

import ir.bank.qh.party.enums.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Legal entity / organization - identity, incorporation and firmographic attributes
 * used for corporate KYC (registration, activity, ownership structure, size).
 * <p>
 * Unlike {@link PersonEntity}, this has its own surrogate {@code ORGANIZATION_ID}
 * primary key (not shared with PARTY_ID) with a unique FK back to {@link PartyEntity}
 * - see {@link PartyEntity} for why.
 */
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "PARTY", name = "ORGANIZATION")
@SequenceGenerator(name = "ORGANIZATION_ID_SEQ", schema = "PARTY", sequenceName = "ORGANIZATION_ID_SEQ", allocationSize = 1)
@Getter
@Setter
public class OrganizationEntity extends BaseEntity {

    @Id
    @Column(name = "ORGANIZATION_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORGANIZATION_ID_SEQ")
    @EqualsAndHashCode.Include
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PARTY_ID", nullable = false, unique = true,
            foreignKey = @ForeignKey(name = "ORGANIZATION_FK_PARTY"))
    private PartyEntity party;

    @Column(name = "REGISTERED_NAME", nullable = false, length = 300)
    private String registeredName;

    @Column(name = "REGISTRATION_NO", length = 80)
    private String registrationNo;

    @Column(name = "REGISTRATION_PLACE_CODE", length = 30)
    private String registrationPlaceCode;

    @Column(name = "REGISTRATION_COUNTRY_CODE", length = 3)
    private String registrationCountryCode;

    @Column(name = "INCORPORATION_DATE")
    private Date incorporationDate;

    @Column(name = "DISSOLUTION_DATE")
    private Date dissolutionDate;

    @Convert(converter = ir.bank.qh.party.converter.YesNoConverter.class)
    @Column(name = "LISTED_COMPANY_FLAG", length = 1, columnDefinition = "char(1) default 'N'")
    private Boolean listedCompany = Boolean.FALSE;

    @Column(name = "ACTIVITY_STATUS_CODE", length = 30)
    @Enumerated(EnumType.STRING)
    private ActivityStatus activityStatus;

    @Column(name = "MAIN_ACTIVITY_DESCRIPTION", length = 1000)
    private String mainActivityDescription;

    @Column(name = "EMPLOYEE_COUNT")
    private Integer employeeCount;

    @Column(name = "ENTERPRISE_SIZE_CODE", length = 30)
    @Enumerated(EnumType.STRING)
    private EnterpriseSize enterpriseSize;

    @Column(name = "OWNERSHIP_TYPE_CODE", length = 30)
    @Enumerated(EnumType.STRING)
    private OwnershipType ownershipType;
}
