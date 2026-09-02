package ir.bank.qh.reference.entity;

import ir.bank.qh.common.entity.BaseEntity;
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
    @Column(name = "ID")
    private Long id;


    @Column(name = "LOAN_TYPE_ID", nullable = false)
    private Long loanTypeId;

    @Column(name = "PLAN_TYPE_ID", nullable = false)
    private Long planTypeId;


    @Column(name = "RECORD_STATUS_CODE", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
