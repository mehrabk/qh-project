package ir.bank.qh.party.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;

import ir.bank.qh.party.enums.GroupStatus;
import ir.bank.qh.party.enums.GroupType;
import ir.bank.qh.party.enums.WorkflowStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A named grouping of parties - family unit, corporate ownership structure, or a set
 * of joint account holders. Essential for beneficial-ownership / UBO tracking in
 * corporate KYC. Membership detail (role, ownership %) lives in
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

    @Column(name = "GROUP_TYPE_CODE", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private GroupType groupType;

    @Column(name = "GROUP_NAME", nullable = false, length = 300)
    private String groupName;

    @Column(name = "STATUS_CODE", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private GroupStatus status = GroupStatus.ACTIVE;

    @Column(name = "WORKFLOW_STATUS_CODE", length = 30)
    @Enumerated(EnumType.STRING)
    private WorkflowStatus workflowStatus;

    @Column(name = "EFFECTIVE_FROM")
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
