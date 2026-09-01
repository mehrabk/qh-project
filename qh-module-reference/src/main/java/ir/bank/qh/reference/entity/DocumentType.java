package ir.bank.qh.reference.entity;

import ir.bank.qh.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Reference catalog of document types required by products (e.g. NATIONAL_ID, INCOME_PROOF).
 * Maps to table DOCUMENT_TYPE.
 */
@Getter
@Setter
@Entity
@Table(schema = "REFERENCE", name = "document_type")
public class DocumentType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;



    @Column(name = "document_type_code", nullable = false, unique = true, length = 30)
    private String documentTypeCode;


    @Column(name = "document_type_name", nullable = false, length = 100)
    private String documentTypeName;


    @Column(name = "record_status_code", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
