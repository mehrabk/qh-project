package ir.bank.qh.party.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;

import ir.bank.qh.party.enums.CaptureChannel;
import ir.bank.qh.party.enums.SignatureStatus;
import ir.bank.qh.party.enums.SigningRule;
import ir.bank.qh.party.enums.VerificationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * A captured signature specimen for a Party, used by branch staff / signature
 * verification systems to validate physical instructions (cheques, withdrawal slips,
 * mandate changes).
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PARTY_ID", referencedColumnName = "PARTY_ID", nullable = false,
            foreignKey = @ForeignKey(name = "SIGNATURE_SPECIMEN_FK_PARTY"))
    private PartyEntity party;

    /**
     * Identifies who is signing when a mandate involves several signatories on
     * behalf of the same Party (e.g. one of several company directors) - distinct
     * from {@code party}, which is always the account/mandate owner.
     */
    @Column(name = "SIGNATORY_ID", length = 50)
    private String signatoryId;

    @Column(name = "SPECIMEN_TYPE_CODE", length = 50)
    private String specimenType;

    @Lob
    @Column(name = "SIGNATURE_IMAGE")
    private byte[] signatureImage;

    @Column(name = "EFFECTIVE_FROM")
    private Date effectiveFrom;

    @Column(name = "EFFECTIVE_TO")
    private Date effectiveTo;

    @Column(name = "STATUS_CODE", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private SignatureStatus status = SignatureStatus.PENDING_VERIFICATION;

    @Column(name = "SIGNING_RULE_CODE", length = 30)
    @Enumerated(EnumType.STRING)
    private SigningRule signingRule;

    @Column(name = "VERIFICATION_STATUS_CODE", length = 30)
    @Enumerated(EnumType.STRING)
    private VerificationStatus verificationStatus = VerificationStatus.UNVERIFIED;

    @Column(name = "CAPTURE_CHANNEL_CODE", length = 30)
    @Enumerated(EnumType.STRING)
    private CaptureChannel captureChannel;

    @Column(name = "DOCUMENT_ID")
    private Long documentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BRANCH_ID", referencedColumnName = "BRANCH_ID",
            foreignKey = @ForeignKey(name = "SIGNATURE_SPECIMEN_FK_BRANCH"))
    private BranchEntity branch;

    @Column(name = "CAPTURED_BY", length = 100)
    private String capturedBy;

    @Column(name = "REVOKED_AT")
    private Date revokedAt;

    @Column(name = "REVOCATION_REASON", length = 500)
    private String revocationReason;
}
