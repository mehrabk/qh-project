package ir.bank.qh.reference.entity;

import ir.bank.qh.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Reference catalog of facility usage/purpose, e.g. PERSONAL_NEED, GOODS_PURCHASE, WORKING_CAPITAL.
 * Maps to table LOAN_USAGE.
 */
@Getter
@Setter
@Entity
@Table(schema = "PRODUCTBUILDER", name = "loan_usage")
public class LoanUsage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;



    @Column(name = "LOAN_USAGE_CODE", nullable = false, unique = true, length = 30)
    private String loanUsageCode;


    @Column(name = "LOAN_USAGE_NAME", nullable = false, length = 100)
    private String loanUsageName;


    @Column(name = "RECORD_STATUS_CODE", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
