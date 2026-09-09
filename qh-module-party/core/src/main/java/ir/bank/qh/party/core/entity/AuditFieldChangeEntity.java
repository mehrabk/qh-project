package ir.bank.qh.party.core.entity;

import ir.bank.qh.party.reference.entity.RefAuditValueTypeEntity;

import ir.bank.qh.party.reference.converter.YesNoConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * جزئیات فیلدهای تغییر یافته برای هر AUDIT_EVENT - برای داده‌های حساس یا باینری مقدار خام
 * ذخیره نمی‌شود و فقط مرجع یا هش نگهداری می‌شود.
 * <p>
 * Immutable child of {@link AuditEventEntity}; not extending {@code BaseEntity} for
 * the same reason (no CREATED_BY, UPDATED_BY or RECORD_VERSION columns in the model).
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "PARTY", name = "AUDIT_FIELD_CHANGE")
@SequenceGenerator(name = "AUDIT_FIELD_CHANGE_ID_SEQ", schema = "PARTY", sequenceName = "AUDIT_FIELD_CHANGE_ID_SEQ", allocationSize = 1)
@Getter
@Setter
public class AuditFieldChangeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUDIT_FIELD_CHANGE_ID_SEQ")
    @Column(name = "AUDIT_FIELD_CHANGE_ID")
    @EqualsAndHashCode.Include
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "AUDIT_EVENT_ID", referencedColumnName = "AUDIT_EVENT_ID", nullable = false,
            foreignKey = @ForeignKey(name = "AUDIT_FIELD_CHANGE_FK_EVENT"))
    private AuditEventEntity auditEvent;

    @Column(name = "FIELD_NAME", nullable = false, length = 128)
    private String fieldName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "VALUE_TYPE_CODE", referencedColumnName = "VALUE_TYPE_CODE", nullable = false,
            foreignKey = @ForeignKey(name = "AUDIT_FIELD_CHANGE_FK_VALUE_TYPE"))
    private RefAuditValueTypeEntity valueType;

    @Column(name = "OLD_VALUE", length = 2000)
    private String oldValue;

    @Column(name = "NEW_VALUE", length = 2000)
    private String newValue;

    @Column(name = "OLD_VALUE_HASH", length = 64)
    private String oldValueHash;

    @Column(name = "NEW_VALUE_HASH", length = 64)
    private String newValueHash;

    @Column(name = "OLD_OBJECT_REF", length = 200)
    private String oldObjectRef;

    @Column(name = "NEW_OBJECT_REF", length = 200)
    private String newObjectRef;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "IS_SENSITIVE", nullable = false, length = 1, columnDefinition = "char(1) default 'N'")
    private Boolean sensitive = Boolean.FALSE;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "IS_REDACTED", nullable = false, length = 1, columnDefinition = "char(1) default 'N'")
    private Boolean redacted = Boolean.FALSE;
}
