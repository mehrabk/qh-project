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
@Table(schema = "PRODUCTBUILDER", name = "document_type")
public class DocumentType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;



    @Column(name = "DOCUMENT_TYPE_CODE", nullable = false, unique = true, length = 30)
    private String documentTypeCode;


    @Column(name = "DOCUMENT_TYPE_NAME", nullable = false, length = 100)
    private String documentTypeName;


    @Column(name = "RECORD_STATUS_CODE", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
