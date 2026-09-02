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
@Table(schema = "PRODUCTBUILDER", name = "loan_type")
public class LoanType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;



    @Column(name = "LOAN_TYPE_CODE", nullable = false, unique = true, length = 30)
    private String loanTypeCode;


    @Column(name = "LOAN_TYPE_NAME", nullable = false, length = 50)
    private String loanTypeName;


    @Column(name = "LOAN_TYPE_CBI_CODE", length = 30)
    private String loanTypeCbiCode;


    @Column(name = "LOAN_GROUP")
    private Integer loanGroup;


    @Column(name = "OBLIGATION_TYPE")
    private Integer obligationType;


    @Column(name = "RECORD_STATUS_CODE", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
