package ir.bank.qh.party.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;

import ir.bank.qh.party.enums.ContextType;
import ir.bank.qh.party.enums.RoleType;
import ir.bank.qh.party.enums.WorkflowStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * The single most important extensibility point in a Party model: a generic,
 * context-scoped role that any Party can play against any business object
 * (an account, a contract, a facility, a case, a group, a product) without needing
 * a bespoke join table per scenario - e.g. "Party X is SIGNATORY on Account 123 with
 * ANY_TWO signing authority" or "Party Y is LEGAL_REPRESENTATIVE of Party Z (the
 * {@code principalParty}) under power-of-attorney document #456".
 */
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "PARTY", name = "PARTY_ROLE")
@SequenceGenerator(name = "PARTY_ROLE_ID_SEQ", schema = "PARTY", sequenceName = "PARTY_ROLE_ID_SEQ", allocationSize = 1)
@Getter
@Setter
public class PartyRoleEntity extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PARTY_ROLE_ID_SEQ")
    @Column(name = "PARTY_ROLE_ID")
    @EqualsAndHashCode.Include
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PARTY_ID", referencedColumnName = "PARTY_ID", nullable = false,
            foreignKey = @ForeignKey(name = "PARTY_ROLE_FK_PARTY"))
    private PartyEntity party;

    @Column(name = "ROLE_TYPE_CODE", nullable = false, length = 40)
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Column(name = "CONTEXT_TYPE_CODE", nullable = false, length = 40)
    @Enumerated(EnumType.STRING)
    private ContextType contextType;

    /** Polymorphic reference to the business object id (account id, contract id, case id, ...). */
    @Column(name = "CONTEXT_ID", nullable = false, length = 100)
    private String contextId;

    /**
     * The other Party this role is exercised on behalf of, e.g. the company being
     * represented when {@code roleType = LEGAL_REPRESENTATIVE}. Null for roles that
     * are not representative in nature (e.g. plain CUSTOMER, SIGNATORY).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRINCIPAL_PARTY_ID", referencedColumnName = "PARTY_ID",
            foreignKey = @ForeignKey(name = "PARTY_ROLE_FK_PRINCIPAL"))
    private PartyEntity principalParty;

    @Column(name = "RELATIONSHIP_TYPE_CODE", length = 40)
    private String relationshipTypeCode;

    @Column(name = "AUTHORITY_BASIS_CODE", length = 30)
    private String authorityBasisCode;

    @Column(name = "AUTHORITY_DOCUMENT_NO", length = 100)
    private String authorityDocumentNo;

    @Column(name = "AUTHORITY_ISSUER", length = 200)
    private String authorityIssuer;

    @Column(name = "AUTHORITY_SCOPE_TEXT", length = 500)
    private String authorityScopeText;

    @Column(name = "ASSIGNMENT_REASON_TEXT", length = 500)
    private String assignmentReasonText;

    @Column(name = "DESCRIPTION_TEXT", length = 500)
    private String descriptionText;

    @Column(name = "VALID_FROM")
    private Date validFrom;

    @Column(name = "VALID_TO")
    private Date validTo;

    @Column(name = "STATUS_CODE", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private WorkflowStatus status = WorkflowStatus.ACTIVE;
}
