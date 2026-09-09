package ir.bank.qh.party.core.entity;

import ir.bank.qh.party.reference.entity.RefGroupMemberRoleEntity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/** عضویت یک PARTY_MEMBERSHIP در یک گروه متعلق به همان صندوق. */
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "PARTY", name = "PARTY_GROUP_MEMBER",
        uniqueConstraints = @UniqueConstraint(name = "UQ_GROUP_MEMBER_PERIOD",
                columnNames = {"GROUP_ID", "PARTY_MEMBERSHIP_ID", "VALID_FROM"}))
@SequenceGenerator(name = "PARTY_GROUP_MEMBER_ID_SEQ", schema = "PARTY", sequenceName = "PARTY_GROUP_MEMBER_ID_SEQ", allocationSize = 1)
@Getter
@Setter
public class PartyGroupMemberEntity extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PARTY_GROUP_MEMBER_ID_SEQ")
    @Column(name = "GROUP_MEMBER_ID")
    @EqualsAndHashCode.Include
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "GROUP_ID", referencedColumnName = "GROUP_ID", nullable = false,
            foreignKey = @ForeignKey(name = "GMEM_FK_GROUP"))
    private PartyGroupEntity group;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PARTY_MEMBERSHIP_ID", referencedColumnName = "PARTY_MEMBERSHIP_ID", nullable = false,
            foreignKey = @ForeignKey(name = "GMEM_FK_MEMBERSHIP"))
    private PartyMembershipEntity partyMembership;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MEMBER_ROLE_CODE", referencedColumnName = "MEMBER_ROLE_CODE", nullable = false,
            foreignKey = @ForeignKey(name = "GMEM_FK_ROLE"))
    private RefGroupMemberRoleEntity memberRole;

    /** Ownership percentage, relevant for OWNERSHIP / CORPORATE_STRUCTURE groups (UBO tracking). */
    @Column(name = "OWNERSHIP_PERCENT", precision = 6, scale = 3)
    private BigDecimal ownershipPercent;

    @Column(name = "VALID_FROM", nullable = false)
    private Date validFrom;

    @Column(name = "VALID_TO")
    private Date validTo;
}
