package ir.bank.qh.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

/**
 * Enables CREATED_AT/CREATED_BY/UPDATED_AT/UPDATED_BY population for every
 * @EntityListeners(AuditingEntityListener.class) entity in every module.
 * <p>
 * In a real deployment {@link #auditorAware()} should read the authenticated
 * principal (e.g. from Spring Security's SecurityContext). For this
 * scaffold/demo it falls back to a fixed system user.
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaAuditingConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("SYSTEM");
    }
}
