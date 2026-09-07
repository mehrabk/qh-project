package ir.bank.qh.party.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * رویداد غیرقابل‌ویرایش برای ثبت هر تغییر در هسته Party یا اطلاعات اختصاصی صندوق - هر
 * عملیات کسب‌وکاری یک Correlation ID مشترک می‌تواند داشته باشد.
 * <p>
 * Deliberately not extending {@code BaseEntity}: an audit event is immutable by
 * design (EVENT_AT/EVENT_BY replace the usual CREATED_AT/CREATED_BY, there is no
 * UPDATED_AT, UPDATED_BY or RECORD_VERSION - nothing about an audit row is ever meant to change).
 * {@code tenantId} is a plain nullable column, not {@code @TenantId}, since a single
 * event stream must cover both core (tenant-independent) and tenant-scoped changes.
 * No automatic capture is wired up in this pass - rows are written explicitly by
 * whichever caller wants to record one.
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "PARTY", name = "AUDIT_EVENT")
@SequenceGenerator(name = "AUDIT_EVENT_ID_SEQ", schema = "PARTY", sequenceName = "AUDIT_EVENT_ID_SEQ", allocationSize = 1)
@Getter
@Setter
public class AuditEventEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUDIT_EVENT_ID_SEQ")
    @Column(name = "AUDIT_EVENT_ID")
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "AUDIT_SCOPE_CODE", referencedColumnName = "AUDIT_SCOPE_CODE", nullable = false,
            foreignKey = @ForeignKey(name = "AUDIT_EVENT_FK_SCOPE"))
    private RefAuditScopeEntity auditScope;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ENTITY_TYPE_CODE", referencedColumnName = "ENTITY_TYPE_CODE", nullable = false,
            foreignKey = @ForeignKey(name = "AUDIT_EVENT_FK_ENTITY_TYPE"))
    private RefAuditEntityTypeEntity entityType;

    /** Id of the changed row within {@code entityType} - polymorphic, so kept as a plain id, not a mapped FK. */
    @Column(name = "ENTITY_ID", nullable = false)
    private Long entityId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARTY_ID", referencedColumnName = "PARTY_ID",
            foreignKey = @ForeignKey(name = "AUDIT_EVENT_FK_PARTY"))
    private PartyEntity party;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARTY_MEMBERSHIP_ID", referencedColumnName = "PARTY_MEMBERSHIP_ID",
            foreignKey = @ForeignKey(name = "AUDIT_EVENT_FK_MEMBERSHIP"))
    private PartyMembershipEntity partyMembership;

    /** Not {@code @TenantId}: null for core-scope events, set for tenant-scope ones - must never be silently filtered. */
    @Column(name = "TENANT_ID")
    private Long tenantId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ACTION_CODE", referencedColumnName = "ACTION_CODE", nullable = false,
            foreignKey = @ForeignKey(name = "AUDIT_EVENT_FK_ACTION"))
    private RefAuditActionEntity action;

    @Column(name = "EVENT_AT", nullable = false)
    private Date eventAt;

    @Column(name = "EVENT_BY", nullable = false, length = 100)
    private String eventBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOURCE_CHANNEL_CODE", referencedColumnName = "CHANNEL_CODE",
            foreignKey = @ForeignKey(name = "AUDIT_EVENT_FK_CHANNEL"))
    private RefChannelEntity sourceChannel;

    /** Free-text originating system identifier; no dedicated system catalog in this model. */
    @Column(name = "SOURCE_SYSTEM_CODE", length = 50)
    private String sourceSystemCode;

    @Column(name = "CORRELATION_ID", nullable = false, length = 64)
    private String correlationId;

    @Column(name = "SESSION_ID", length = 100)
    private String sessionId;

    @Column(name = "CHANGE_REASON", length = 500)
    private String changeReason;

    @Column(name = "RECORD_VERSION_BEFORE")
    private Long recordVersionBefore;

    @Column(name = "RECORD_VERSION_AFTER")
    private Long recordVersionAfter;

    @Column(name = "CLIENT_IP", length = 45)
    private String clientIp;

    @Column(name = "EVENT_HASH", length = 64)
    private String eventHash;

    @Column(name = "ORIGIN_TENANT_ID")
    private Long originTenantId;
}
