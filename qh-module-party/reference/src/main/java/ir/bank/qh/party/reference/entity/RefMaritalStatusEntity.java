package ir.bank.qh.party.reference.entity;

import ir.bank.qh.common.entity.BaseEntity;
import ir.bank.qh.party.reference.converter.YesNoConverter;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * وضعیت تأهل شخص حقیقی.
 * Maps to table REF_MARITAL_STATUS.
 */
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "PARTY", name = "REF_MARITAL_STATUS")
@Getter
@Setter
public class RefMaritalStatusEntity extends BaseEntity {

    // Named "id" (not "code") so JpaReferenceResolver - which looks up a field literally
    // named "id" to resolve a client-supplied {"id": ...} reference - works unmodified for
    // this natural-key (String) PK the same way it already does for every Long-id entity.
    @Id
    @Column(name = "MARITAL_STATUS_CODE", length = 50)
    @EqualsAndHashCode.Include
    private String id;

    @Column(name = "TITLE_FA", nullable = false, length = 200)
    private String titleFa;

    @Column(name = "DESCRIPTION_FA", length = 500)
    private String descriptionFa;

    @Column(name = "DISPLAY_ORDER", nullable = false, columnDefinition = "number default 0")
    private Integer displayOrder = 0;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "IS_ACTIVE", nullable = false, length = 1, columnDefinition = "char(1) default 'Y'")
    private Boolean active = Boolean.TRUE;

}
