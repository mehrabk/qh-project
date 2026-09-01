package ir.bank.qh.party.entity;

import ir.bank.qh.common.entity.TenantAwareEntity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Branch of the servicing institution. Tenant-scoped: each institution manages its
 * own set of branches. Referenced by SIGNATURE_SPECIMEN and PARTY_INQUIRY for
 * "where was this captured / who is the servicing branch" traceability.
 */
@Entity
@Table(schema = "PARTY", name = "BRANCH", uniqueConstraints = @UniqueConstraint(columnNames = {"INSTITUTION_ID", "BRANCH_CODE"}))
@SequenceGenerator(name = "BRANCH_ID_SEQ", schema = "PARTY", sequenceName = "BRANCH_ID_SEQ", allocationSize = 1)
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Getter
@Setter
public class BranchEntity extends TenantAwareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BRANCH_ID_SEQ")
    @Column(name = "BRANCH_ID")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "BRANCH_CODE", nullable = false, length = 30)
    private String branchCode;

    @Column(name = "BRANCH_NAME", nullable = false, length = 200)
    private String branchName;
}
