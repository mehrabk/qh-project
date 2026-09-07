package ir.bank.qh.party.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * نمونه امضای ثبت‌شده برای عضویت پارتی در یک صندوق - نمونه امضا می‌تواند بین صندوق‌های
 * مختلف متفاوت باشد.
 */
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "PARTY", name = "SIGNATURE_SPECIMEN")
@SequenceGenerator(name = "SIGNATURE_SPECIMEN_ID_SEQ", schema = "PARTY", sequenceName = "SIGNATURE_SPECIMEN_ID_SEQ", allocationSize = 1)
@Getter
@Setter
public class SignatureSpecimenEntity extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIGNATURE_SPECIMEN_ID_SEQ")
    @Column(name = "SIGNATURE_ID")
    @EqualsAndHashCode.Include
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PARTY_MEMBERSHIP_ID", referencedColumnName = "PARTY_MEMBERSHIP_ID", nullable = false,
            foreignKey = @ForeignKey(name = "SIGNATURE_FK_MEMBERSHIP"))
    private PartyMembershipEntity partyMembership;

    /**
     * Identifies who is signing when a mandate involves several signatories on
     * behalf of the same membership (e.g. one of several company directors) -
     * distinct from {@code partyMembership}, which is always the account/mandate
     * owner.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SIGNATORY_MEMBERSHIP_ID", referencedColumnName = "PARTY_MEMBERSHIP_ID", nullable = false,
            foreignKey = @ForeignKey(name = "SIGNATURE_FK_SIGNATORY_MEMBERSHIP"))
    private PartyMembershipEntity signatoryMembership;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SPECIMEN_TYPE_CODE", referencedColumnName = "SPECIMEN_TYPE_CODE", nullable = false,
            foreignKey = @ForeignKey(name = "SIGNATURE_FK_SPECIMEN_TYPE"))
    private RefSpecimenTypeEntity specimenType;

    @Lob
    @Column(name = "SIGNATURE_IMAGE", nullable = false)
    private byte[] signatureImage;

    @Column(name = "EFFECTIVE_FROM", nullable = false)
    private Date effectiveFrom;

    @Column(name = "EFFECTIVE_TO")
    private Date effectiveTo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "STATUS_CODE", referencedColumnName = "STATUS_CODE", nullable = false,
            foreignKey = @ForeignKey(name = "SIGNATURE_FK_STATUS"))
    private RefWorkflowStatusEntity status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SIGNING_RULE_CODE", referencedColumnName = "SIGNING_RULE_CODE", nullable = false,
            foreignKey = @ForeignKey(name = "SIGNATURE_FK_SIGNING_RULE"))
    private RefSigningRuleEntity signingRule;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "VERIFICATION_STATUS_CODE", referencedColumnName = "VERIFICATION_STATUS_CODE", nullable = false,
            foreignKey = @ForeignKey(name = "SIGNATURE_FK_VERIFICATION_STATUS"))
    private RefVerificationStatusEntity verificationStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CAPTURE_CHANNEL_CODE", referencedColumnName = "CHANNEL_CODE", nullable = false,
            foreignKey = @ForeignKey(name = "SIGNATURE_FK_CAPTURE_CHANNEL"))
    private RefChannelEntity captureChannel;

    /** Reference to a document-management record; no dedicated document catalog in this model. */
    @Column(name = "DOCUMENT_ID")
    private Long documentId;

    /** Reference to a branch record; no dedicated branch catalog in this model. */
    @Column(name = "BRANCH_ID")
    private Long branchId;

    @Column(name = "CAPTURED_BY", nullable = false, length = 100)
    private String capturedBy;

    @Column(name = "REVOKED_AT")
    private Date revokedAt;

    @Column(name = "REVOCATION_REASON", length = 200)
    private String revocationReason;
}
