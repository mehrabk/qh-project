package ir.bank.qh.party.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;

import ir.bank.qh.party.enums.InquiryStatus;
import ir.bank.qh.party.enums.InquiryType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * An immutable record of an external compliance check run against a Party - KYC
 * verification, AML/sanctions-list screening, PEP check, or credit-bureau lookup.
 * <p>
 * This entity did not exist in the original Party diagram; it is carried over from a
 * sibling implementation reviewed alongside this model because a bank's Party
 * subsystem needs an auditable trail of *when* and *what* compliance checks were
 * performed and with what result, independent of the Party's current verification
 * status fields (which only reflect the latest outcome).
 */
@Entity
@Table(schema = "PARTY", name = "PARTY_INQUIRY")
@SequenceGenerator(name = "PARTY_INQUIRY_ID_SEQ", schema = "PARTY", sequenceName = "PARTY_INQUIRY_ID_SEQ", allocationSize = 1)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Getter
@Setter
public class PartyInquiryEntity extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PARTY_INQUIRY_ID_SEQ")
    @Column(name = "PARTY_INQUIRY_ID")
    @EqualsAndHashCode.Include
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PARTY_ID", referencedColumnName = "PARTY_ID", nullable = false,
            foreignKey = @ForeignKey(name = "PARTY_INQUIRY_FK_PARTY"))
    private PartyEntity party;

    @Column(name = "INQUIRY_TYPE_CODE", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private InquiryType inquiryType;

    @Lob
    @Column(name = "RESPONSE")
    private String response;

    @Column(name = "RESULT_VALUE")
    private Integer resultValue;

    @Lob
    @Column(name = "HEADER_RESPONSE")
    private String headerResponse;

    @Column(name = "STATUS_CODE", length = 30)
    @Enumerated(EnumType.STRING)
    private InquiryStatus status;

    /** When the external inquiry itself was made - distinct from {@link #getCreatedOn()}, the audit timestamp for this row. */
    @Column(name = "INQUIRY_DATE")
    private LocalDateTime inquiryDate;

    @Column(name = "EXPIRATION_DATE")
    private LocalDateTime expirationDate;
}
