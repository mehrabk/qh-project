package ir.bank.qh.party.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * گروه پارتی‌ها در دامنه یک صندوق مشخص.
 * <p>
 * A named grouping of party memberships - family unit, corporate ownership
 * structure, or a set of joint account holders. Essential for beneficial-ownership
 * / UBO tracking in corporate KYC. Membership detail (role, ownership %) lives in
 * {@link PartyGroupMemberEntity}.
 */
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "PARTY", name = "PARTY_GROUP")
@SequenceGenerator(name = "PARTY_GROUP_ID_SEQ", schema = "PARTY", sequenceName = "PARTY_GROUP_ID_SEQ", allocationSize = 1)
@Getter
@Setter
public class PartyGroupEntity extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PARTY_GROUP_ID_SEQ")
    @Column(name = "GROUP_ID")
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "GROUP_TYPE_CODE", referencedColumnName = "GROUP_TYPE_CODE", nullable = false,
            foreignKey = @ForeignKey(name = "PARTY_GROUP_FK_TYPE"))
    private RefGroupTypeEntity groupType;

    @Column(name = "GROUP_NAME", nullable = false, length = 300)
    private String groupName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "STATUS_CODE", referencedColumnName = "STATUS_CODE", nullable = false,
            foreignKey = @ForeignKey(name = "PARTY_GROUP_FK_STATUS"))
    private RefWorkflowStatusEntity status;

    @Column(name = "EFFECTIVE_FROM", nullable = false)
    private Date effectiveFrom;

    @Column(name = "EFFECTIVE_TO")
    private Date effectiveTo;

    @JsonIgnoreProperties("group")
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PartyGroupMemberEntity> members = new ArrayList<>();

    public void addMember(PartyGroupMemberEntity member) {
        member.setGroup(this);
        this.members.add(member);
    }
}
