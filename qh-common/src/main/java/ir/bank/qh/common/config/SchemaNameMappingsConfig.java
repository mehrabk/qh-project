package ir.bank.qh.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Binds every property under {@code qh.schemas.*} (e.g. {@code qh.schemas.core: CORE}) directly
 * into a flat {@code logical -> physical} map, consumed by {@link SchemaProperties}.
 * <p>
 * {@code @ConfigurationProperties} bound to a POJO field would instead require one extra level
 * ({@code qh.schemas.mappings.core}); binding straight to the {@code Map} the {@code @Bean} method
 * returns keeps the property path exactly {@code qh.schemas.<logical-name>}.
 */
@Configuration
public class SchemaNameMappingsConfig {

    @Bean
    @ConfigurationProperties(prefix = "qh.schemas")
    public Map<String, String> schemaNameMappings() {
        return new HashMap<>();
    }
}
