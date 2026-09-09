package ir.bank.qh.party.core.entity;

import ir.bank.qh.party.reference.entity.RefCountryEntity;
import ir.bank.qh.party.reference.entity.RefEnterpriseSizeEntity;
import ir.bank.qh.party.reference.entity.RefOrgActivityStatusEntity;
import ir.bank.qh.party.reference.entity.RefOwnershipTypeEntity;

import ir.bank.qh.common.entity.BaseEntity;
import ir.bank.qh.party.reference.converter.YesNoConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * مشخصات هویتی پایه شخص حقوقی در هسته مرکزی - اطلاعات این جدول بین صندوق‌ها تکرار نمی‌شود.
 * <p>
 * Unlike PERSON, the model gives ORGANIZATION its own surrogate ORGANIZATION_ID,
 * separate from PARTY_ID - modeled here as a plain (non-{@code @MapsId}) 1:1 FK
 * to PARTY.
 */
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "PARTY", name = "ORGANIZATION")
@SequenceGenerator(name = "ORGANIZATION_ID_SEQ", schema = "PARTY", sequenceName = "ORGANIZATION_ID_SEQ", allocationSize = 1)
@Getter
@Setter
public class OrganizationEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORGANIZATION_ID_SEQ")
    @Column(name = "ORGANIZATION_ID")
    @EqualsAndHashCode.Include
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PARTY_ID", referencedColumnName = "PARTY_ID", nullable = false, unique = true,
            foreignKey = @ForeignKey(name = "ORGANIZATION_FK_PARTY"))
    private PartyEntity party;

    @Column(name = "REGISTERED_NAME", nullable = false, length = 300)
    private String registeredName;

    @Column(name = "REGISTRATION_NO", nullable = false, length = 80)
    private String registrationNo;

    /** Free-text place of registration (e.g. a specific registrar office); no dedicated reference catalog in the model. */
    @Column(name = "REGISTRATION_PLACE_CODE", length = 30)
    private String registrationPlaceCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "REGISTRATION_COUNTRY_CODE", referencedColumnName = "COUNTRY_CODE", nullable = false,
            foreignKey = @ForeignKey(name = "ORGANIZATION_FK_REG_COUNTRY"))
    private RefCountryEntity registrationCountry;

    @Column(name = "INCORPORATION_DATE", nullable = false)
    private Date incorporationDate;

    @Column(name = "DISSOLUTION_DATE")
    private Date dissolutionDate;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "LISTED_COMPANY_FLAG", nullable = false, length = 1, columnDefinition = "char(1) default 'N'")
    private Boolean listedCompany = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ACTIVITY_STATUS_CODE", referencedColumnName = "ACTIVITY_STATUS_CODE", nullable = false,
            foreignKey = @ForeignKey(name = "ORGANIZATION_FK_ACTIVITY_STATUS"))
    private RefOrgActivityStatusEntity activityStatus;

    @Column(name = "MAIN_ACTIVITY_DESCRIPTION", nullable = false, length = 1000)
    private String mainActivityDescription;

    @Column(name = "EMPLOYEE_COUNT")
    private Integer employeeCount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ENTERPRISE_SIZE_CODE", referencedColumnName = "ENTERPRISE_SIZE_CODE", nullable = false,
            foreignKey = @ForeignKey(name = "ORGANIZATION_FK_ENTERPRISE_SIZE"))
    private RefEnterpriseSizeEntity enterpriseSize;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "OWNERSHIP_TYPE_CODE", referencedColumnName = "OWNERSHIP_TYPE_CODE", nullable = false,
            foreignKey = @ForeignKey(name = "ORGANIZATION_FK_OWNERSHIP_TYPE"))
    private RefOwnershipTypeEntity ownershipType;
}
