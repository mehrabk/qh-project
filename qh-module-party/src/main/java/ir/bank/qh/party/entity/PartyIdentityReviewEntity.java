package ir.bank.qh.party.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * صف مرکزی و محرمانه برای بررسی اختلاف بین اطلاعات هویتی ارسال‌شده توسط صندوق و رکورد
 * canonical Party - این جدول فقط برای سرویس/کاربران مرکزی قابل مشاهده است و نباید وجود
 * Party قبلی یا مقادیر canonical را به صندوق افشا کند.
 * <p>
 * Does not extend {@code BaseEntity}/{@code TenantAwareEntity}: the model gives it
 * its own REQUESTED_AT/REQUESTED_BY audit pair instead of CREATED_AT/CREATED_BY, and
 * {@code sourceTenantId} is deliberately a plain column (not {@code @TenantId}) since
 * this queue must remain readable by the central compliance team across every tenant.
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "PARTY", name = "PARTY_IDENTITY_REVIEW")
@SequenceGenerator(name = "PARTY_IDENTITY_REVIEW_ID_SEQ", schema = "PARTY", sequenceName = "PARTY_IDENTITY_REVIEW_ID_SEQ", allocationSize = 1)
@Getter
@Setter
public class PartyIdentityReviewEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PARTY_IDENTITY_REVIEW_ID_SEQ")
    @Column(name = "IDENTITY_REVIEW_ID")
    @EqualsAndHashCode.Include
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PARTY_ID", referencedColumnName = "PARTY_ID", nullable = false,
            foreignKey = @ForeignKey(name = "IDENTITY_REVIEW_FK_PARTY"))
    private PartyEntity party;

    /** The tenant whose submitted data conflicted with the canonical Party record - not a {@code @TenantId}, must stay cross-tenant readable. */
    @Column(name = "SOURCE_TENANT_ID", nullable = false)
    private Long sourceTenantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARTY_MEMBERSHIP_ID", referencedColumnName = "PARTY_MEMBERSHIP_ID",
            foreignKey = @ForeignKey(name = "IDENTITY_REVIEW_FK_MEMBERSHIP"))
    private PartyMembershipEntity partyMembership;

    @Column(name = "REQUEST_CORRELATION_ID", nullable = false, length = 64)
    private String requestCorrelationId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "REVIEW_STATUS_CODE", referencedColumnName = "REVIEW_STATUS_CODE", nullable = false,
            foreignKey = @ForeignKey(name = "IDENTITY_REVIEW_FK_STATUS"))
    private RefIdentityReviewStatusEntity reviewStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "REVIEW_REASON_CODE", referencedColumnName = "REVIEW_REASON_CODE", nullable = false,
            foreignKey = @ForeignKey(name = "IDENTITY_REVIEW_FK_REASON"))
    private RefIdentityReviewReasonEntity reviewReason;

    @Column(name = "SUBMITTED_IDENTITY_DATA", nullable = false, length = 2000)
    private String submittedIdentityData;

    @Column(name = "CONFLICT_FIELD_LIST", length = 1000)
    private String conflictFieldList;

    @Column(name = "REQUESTED_AT", nullable = false)
    private Date requestedAt;

    @Column(name = "REQUESTED_BY", nullable = false, length = 100)
    private String requestedBy;

    @Column(name = "RESOLVED_AT")
    private Date resolvedAt;

    @Column(name = "RESOLVED_BY", length = 100)
    private String resolvedBy;

    @Column(name = "RESOLUTION_NOTE", length = 1000)
    private String resolutionNote;

    @JsonIgnore
    @Version
    @Column(name = "RECORD_VERSION", columnDefinition = "integer default 1")
    private Long version;
}
