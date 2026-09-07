package ir.bank.qh.party.entity;

import ir.bank.qh.common.entity.BaseEntity;
import ir.bank.qh.party.converter.YesNoConverter;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * نوع رابطه Party با نشانی در هر صندوق.
 * Maps to table REF_ADDRESS_TYPE.
 */
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "PARTY", name = "REF_ADDRESS_TYPE")
@Getter
@Setter
public class RefAddressTypeEntity extends BaseEntity {

    // Named "id" (not "code") so JpaReferenceResolver - which looks up a field literally
    // named "id" to resolve a client-supplied {"id": ...} reference - works unmodified for
    // this natural-key (String) PK the same way it already does for every Long-id entity.
    @Id
    @Column(name = "ADDRESS_TYPE_CODE", length = 50)
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
