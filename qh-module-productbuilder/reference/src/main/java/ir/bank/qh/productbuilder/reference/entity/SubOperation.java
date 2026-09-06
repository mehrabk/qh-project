package ir.bank.qh.productbuilder.reference.entity;

import ir.bank.qh.common.entity.BaseEntity;
import ir.bank.qh.common.enums.RecordStatus;
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
@Table(schema = "PRODUCTBUILDER", name = "sub_operation")
public class SubOperation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;



    @Column(name = "SUB_OPERATION_CODE", nullable = false, unique = true, length = 30)
    private String subOperationCode;


    @Column(name = "SUB_OPERATION_NAME", nullable = false, length = 100)
    private String subOperationName;


    @Column(name = "SUB_OPERATION_TYPE")
    private Integer subOperationType;


    @Column(name = "RECORD_STATUS_CODE", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private RecordStatus recordStatusCode = RecordStatus.ACTIVE;
}
