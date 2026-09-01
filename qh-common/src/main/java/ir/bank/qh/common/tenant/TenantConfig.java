package ir.bank.qh.common.tenant;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.boot.hibernate.autoconfigure.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Registers the Spring-managed {@link TenantIdentifierResolver} bean directly with
 * Hibernate (rather than a class name Hibernate would have to instantiate itself),
 * so it can participate in the Spring application context like any other bean.
 */
@Configuration
public class TenantConfig {

    @Bean
    public HibernatePropertiesCustomizer hibernateTenantResolverCustomizer(
            TenantIdentifierResolver tenantIdentifierResolver) {
        return properties -> properties.put(
                AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, tenantIdentifierResolver);
    }
}
