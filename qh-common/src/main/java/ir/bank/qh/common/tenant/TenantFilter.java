package ir.bank.qh.common.tenant;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Reads the {@code X-Institution-Id} request header (if present) and stores it in
 * {@link TenantContext} for the duration of the request, so every tenant-aware
 * entity read/written during that request is scoped to the right institution.
 * <p>
 * Falls back to {@link TenantContext#DEFAULT_TENANT_ID} when the header is absent,
 * which is enough for the single-institution demo data seeded by data-party.sql.
 * In a real deployment this would typically be derived from the authenticated
 * principal instead of a raw header.
 */
@Component
@Order(1)
public class TenantFilter extends OncePerRequestFilter {

    public static final String TENANT_HEADER = "X-Institution-Id";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        try {
            String header = request.getHeader(TENANT_HEADER);
            if (header != null && !header.isBlank()) {
                TenantContext.setTenantId(Long.valueOf(header.trim()));
            } else {
                TenantContext.setTenantId(TenantContext.DEFAULT_TENANT_ID);
            }
            chain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }
}
