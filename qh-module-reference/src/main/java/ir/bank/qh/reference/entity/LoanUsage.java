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
@Table(schema = "REFERENCE", name = "loan_usage")
public class LoanUsage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;



    @Column(name = "loan_usage_code", nullable = false, unique = true, length = 30)
    private String loanUsageCode;


    @Column(name = "loan_usage_name", nullable = false, length = 100)
    private String loanUsageName;


    @Column(name = "record_status_code", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
