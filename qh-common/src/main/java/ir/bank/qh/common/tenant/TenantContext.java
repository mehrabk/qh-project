package ir.bank.qh.common.tenant;

/**
 * Holds the "current institution" (tenant) for the duration of one request/thread.
 * <p>
 * Backs {@link TenantIdentifierResolver}, which Hibernate consults whenever it needs
 * to stamp or filter by the {@code @TenantId}-annotated {@code institutionId} column
 * declared on {@code TenantAwareEntity} (see qh-common's entity package).
 * <p>
 * Defaults to institution {@code 1L} (the single demo institution seeded by
 * data-party.sql) so the module works out of the box; wire {@link TenantFilter} (or
 * your own interceptor) to call {@link #setTenantId(Long)} from an authenticated
 * principal or a request header in a real multi-institution deployment.
 */
public final class TenantContext {

    public static final Long DEFAULT_TENANT_ID = 1L;

    private static final ThreadLocal<Long> CURRENT_TENANT = ThreadLocal.withInitial(() -> DEFAULT_TENANT_ID);

    private TenantContext() {
    }

    public static void setTenantId(Long tenantId) {
        CURRENT_TENANT.set(tenantId != null ? tenantId : DEFAULT_TENANT_ID);
    }

    public static Long getTenantId() {
        return CURRENT_TENANT.get();
    }

    public static void clear() {
        CURRENT_TENANT.remove();
    }
}
