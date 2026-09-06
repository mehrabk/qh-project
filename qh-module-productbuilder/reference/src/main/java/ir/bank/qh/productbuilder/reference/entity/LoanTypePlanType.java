package ir.bank.qh.productbuilder.reference.entity;

import ir.bank.qh.common.entity.BaseEntity;
import ir.bank.qh.common.enums.RecordStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Junction: which plan types are allowed for which loan types.
 * Maps to table LOAN_TYPE_PLAN_TYPE.
 */
@Getter
@Setter
@Entity
@Table(schema = "PRODUCTBUILDER", name = "loan_type_plan_type")
public class LoanTypePlanType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOAN_TYPE_ID", nullable = false)
    private LoanType loanType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAN_TYPE_ID", nullable = false)
    private PlanType planType;


    @Column(name = "RECORD_STATUS_CODE", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private RecordStatus recordStatusCode = RecordStatus.ACTIVE;
}
