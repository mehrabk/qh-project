package ir.bank.qh.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * Adds the IS_DELETED logical-delete flag used by tables such as PRODUCT.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class SoftDeletableEntity extends TenantAwareEntity {

    @Column(name = "is_deleted", nullable = false)
    private Boolean deleted = Boolean.FALSE;
}
