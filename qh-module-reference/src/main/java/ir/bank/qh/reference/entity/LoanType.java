package ir.bank.qh.reference.entity;

import ir.bank.qh.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Reference catalog of loan/contract (Aqd) types, e.g. QARD_HASAN, MURABAHA, IJARA.
 * Maps to table LOAN_TYPE.
 */
@Getter
@Setter
@Entity
@Table(schema = "REFERENCE", name = "loan_type")
public class LoanType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;



    @Column(name = "loan_type_code", nullable = false, unique = true, length = 30)
    private String loanTypeCode;


    @Column(name = "loan_type_name", nullable = false, length = 50)
    private String loanTypeName;


    @Column(name = "loan_type_cbi_code", length = 30)
    private String loanTypeCbiCode;


    @Column(name = "loan_group")
    private Integer loanGroup;


    @Column(name = "obligation_type")
    private Integer obligationType;


    @Column(name = "record_status_code", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
