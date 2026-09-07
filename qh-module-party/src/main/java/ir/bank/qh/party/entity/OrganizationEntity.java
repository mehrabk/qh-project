package ir.bank.qh.party.entity;

import ir.bank.qh.party.enums.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Legal entity / organization - identity, incorporation and firmographic attributes
 * used for corporate KYC (registration, activity, ownership structure, size).
 */
@Entity
@Table(schema = "PARTY", name = "ORGANIZATION")
@PrimaryKeyJoinColumn(name = "PARTY_ID", foreignKey = @ForeignKey(name = "ORGANIZATION_FK_PARTY"))
@DiscriminatorValue("ORGANIZATION")
@Getter
@Setter
public class OrganizationEntity extends PartyEntity {

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

    @Override
    public PartyType getPartyType() {
        return PartyType.ORGANIZATION;
    }
}
