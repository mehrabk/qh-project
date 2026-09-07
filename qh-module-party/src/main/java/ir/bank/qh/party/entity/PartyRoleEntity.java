package ir.bank.qh.party.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * نقش‌های یک Party در هر صندوق، از جمله مشتری، وکیل، فروشنده، ضامن، نماینده و سایر نقش‌ها.
 * <p>
 * The single most important extensibility point in the Party model: a generic,
 * context-scoped role that any party membership can play against any business
 * object, optionally on behalf of another membership (e.g. a legal representative
 * acting for an organization under a power of attorney).
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
    @JoinColumn(name = "PARTY_MEMBERSHIP_ID", referencedColumnName = "PARTY_MEMBERSHIP_ID", nullable = false,
            foreignKey = @ForeignKey(name = "PARTY_ROLE_FK_MEMBERSHIP"))
    private PartyMembershipEntity partyMembership;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ROLE_TYPE_CODE", referencedColumnName = "ROLE_TYPE_CODE", nullable = false,
            foreignKey = @ForeignKey(name = "PARTY_ROLE_FK_ROLE_TYPE"))
    private RefRoleTypeEntity roleType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTEXT_TYPE_CODE", referencedColumnName = "CONTEXT_TYPE_CODE",
            foreignKey = @ForeignKey(name = "PARTY_ROLE_FK_CONTEXT_TYPE"))
    private RefContextTypeEntity contextType;

    /** Polymorphic reference to the business object id (account id, contract id, case id, ...). */
    @Column(name = "CONTEXT_ID", length = 100)
    private String contextId;

    @Column(name = "VALID_FROM", nullable = false)
    private Date validFrom;

    @Column(name = "VALID_TO")
    private Date validTo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "STATUS_CODE", referencedColumnName = "STATUS_CODE", nullable = false,
            foreignKey = @ForeignKey(name = "PARTY_ROLE_FK_STATUS"))
    private RefWorkflowStatusEntity status;

    /**
     * The other membership this role is exercised on behalf of, e.g. the
     * organization being represented when {@code roleType = LEGAL_REPRESENTATIVE}.
     * Null for roles that are not representative in nature (e.g. plain CUSTOMER).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRINCIPAL_MEMBERSHIP_ID", referencedColumnName = "PARTY_MEMBERSHIP_ID",
            foreignKey = @ForeignKey(name = "PARTY_ROLE_FK_PRINCIPAL_MEMBERSHIP"))
    private PartyMembershipEntity principalMembership;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RELATIONSHIP_TYPE_CODE", referencedColumnName = "RELATIONSHIP_TYPE_CODE",
            foreignKey = @ForeignKey(name = "PARTY_ROLE_FK_RELATIONSHIP_TYPE"))
    private RefRelationshipTypeEntity relationshipType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHORITY_BASIS_CODE", referencedColumnName = "AUTHORITY_BASIS_CODE",
            foreignKey = @ForeignKey(name = "PARTY_ROLE_FK_AUTHORITY_BASIS"))
    private RefAuthorityBasisEntity authorityBasis;

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
}
