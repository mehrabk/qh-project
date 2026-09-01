package ir.bank.qh.reference.entity;

import ir.bank.qh.common.entity.BaseEntity;
import ir.bank.qh.reference.entity.LoanType;
import ir.bank.qh.reference.entity.PlanType;
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
@Table(schema = "REFERENCE", name = "loan_type_plan_type")
public class LoanTypePlanType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_type_id", nullable = false)
    private LoanType loanType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_type_id", nullable = false)
    private PlanType planType;


    @Column(name = "record_status_code", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
