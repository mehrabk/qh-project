package ir.bank.qh.party.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;

import ir.bank.qh.party.enums.MemberRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * A Party's membership in a {@link PartyGroupEntity}, e.g. "Party X owns 35% of the
 * company group" or "Party Y is the legal guardian within this family group".
 */
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "PARTY", name = "PARTY_GROUP_MEMBER",
        uniqueConstraints = @UniqueConstraint(name = "UQ_GROUP_MEMBER_PERIOD",
                columnNames = {"GROUP_ID", "PARTY_ID", "VALID_FROM"}))
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
    @JoinColumn(name = "PARTY_ID", referencedColumnName = "PARTY_ID", nullable = false,
            foreignKey = @ForeignKey(name = "GMEM_FK_PARTY"))
    private PartyEntity party;

    @Column(name = "MEMBER_ROLE_CODE", nullable = false, length = 40)
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    /** Ownership percentage, relevant for OWNERSHIP / CORPORATE_STRUCTURE groups (UBO tracking). */
    @Column(name = "OWNERSHIP_PERCENT", precision = 9, scale = 6)
    private BigDecimal ownershipPercent;

    @Column(name = "VALID_FROM")
    private Date validFrom;

    @Column(name = "VALID_TO")
    private Date validTo;
}
