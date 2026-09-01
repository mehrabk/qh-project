package ir.bank.qh.common.tenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

/**
 * Tells Hibernate which institution (tenant) is "current" for every entity that
 * extends {@code TenantAwareEntity} (its {@code @TenantId institutionId} column).
 * Hibernate stamps this value on insert and transparently adds it to the WHERE
 * clause of every query against a tenant-aware entity - this is what gives each
 * institution row-level data isolation while sharing the same physical schema.
 */
@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver<Long> {

    @Override
    public Long resolveCurrentTenantIdentifier() {
        return TenantContext.getTenantId();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
