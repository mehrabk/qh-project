package ir.bank.qh.party.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * KYC/AML demographic profile of a Party (occupation, education, source of funds,
 * estimated income). One-to-one with Party. Carried over from a sibling
 * implementation reviewed alongside this model because this data is required for
 * risk-scoring and regulatory due diligence and was missing from the original
 * diagram.
 */
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "PARTY", name = "PARTY_DEMOGRAPHIC")
@SequenceGenerator(name = "PARTY_DEMOGRAPHIC_ID_SEQ", schema = "PARTY", sequenceName = "PARTY_DEMOGRAPHIC_ID_SEQ", allocationSize = 1)
@Getter
@Setter
public class PartyDemographicEntity extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PARTY_DEMOGRAPHIC_ID_SEQ")
    @Column(name = "PARTY_DEMOGRAPHIC_ID")
    @EqualsAndHashCode.Include
    private Long id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PARTY_ID", referencedColumnName = "PARTY_ID", nullable = false, unique = true,
            foreignKey = @ForeignKey(name = "PARTY_DEMOGRAPHIC_FK_PARTY"))
    private PartyEntity party;

    @Column(name = "OCCUPATION_OR_INDUSTRY", length = 200)
    private String occupationOrIndustry;

    @Column(name = "EMPLOYER_NAME", length = 200)
    private String employerName;

    @Column(name = "EDUCATION_LEVEL", length = 100)
    private String educationLevel;

    @Column(name = "SOURCE_OF_FUNDS", length = 200)
    private String sourceOfFunds;

    @Column(name = "ESTIMATED_ANNUAL_INCOME", precision = 18, scale = 2)
    private BigDecimal estimatedAnnualIncome;
}
