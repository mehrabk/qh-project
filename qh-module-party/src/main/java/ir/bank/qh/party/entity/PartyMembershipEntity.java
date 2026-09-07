package ir.bank.qh.party.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Links a Party to a Customer relationship, allowing several parties to belong to the
 * same customer number (e.g. joint account holders sharing one customer profile).
 */
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "PARTY", name = "PARTY_MEMBERSHIP")
@SequenceGenerator(name = "PARTY_MEMBERSHIP_ID_SEQ", schema = "PARTY", sequenceName = "PARTY_MEMBERSHIP_ID_SEQ", allocationSize = 1)
@Getter
@Setter
public class PartyMembershipEntity extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PARTY_MEMBERSHIP_ID_SEQ")
    @Column(name = "PARTY_MEMBERSHIP_ID")
    @EqualsAndHashCode.Include
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PARTY_ID", referencedColumnName = "PARTY_ID", nullable = false,
            foreignKey = @ForeignKey(name = "FK_PARTY_MEMBERSHIP_PARTY"))
    private PartyEntity party;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CUSTOMER_NO", referencedColumnName = "CUSTOMER_NO", nullable = false,
            foreignKey = @ForeignKey(name = "FK_PARTY_MEMBERSHIP_CUSTOMER"))
    private CustomerEntity customer;
}
