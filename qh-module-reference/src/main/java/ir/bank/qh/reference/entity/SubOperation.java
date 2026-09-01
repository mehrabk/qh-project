package ir.bank.qh.reference.entity;

import ir.bank.qh.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Reference catalog of granular sub-operations, e.g. PRINCIPAL_PAYMENT, FEE_COLLECTION, REVERSAL.
 * Maps to table SUB_OPERATION.
 */
@Getter
@Setter
@Entity
@Table(schema = "REFERENCE", name = "sub_operation")
public class SubOperation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;



    @Column(name = "sub_operation_code", nullable = false, unique = true, length = 30)
    private String subOperationCode;


    @Column(name = "sub_operation_name", nullable = false, length = 100)
    private String subOperationName;


    @Column(name = "sub_operation_type")
    private Integer subOperationType;


    @Column(name = "record_status_code", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
