package ir.bank.qh.common.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Map;

/**
 * Per-module physical schema names, externalized to configuration under {@code qh.schemas.*}
 * (e.g. {@code qh.schemas.core: CORE}) instead of being hard-coded as {@code @Table(schema = ...)}
 * literals.
 * <p>
 * Every entity keeps its {@code @Table(schema = "CORE")} etc. as a stable <b>logical</b> schema
 * name - the module identifier itself never changes. {@link ConfigurableSchemaNamingStrategy}
 * intercepts that logical name at DDL/SQL-generation time and resolves it through this class to
 * whatever <b>physical</b> schema the active profile configures, so moving a module to a
 * differently named schema (or, later, a dedicated database/server per module) is a config change,
 * not a code change. The actual {@code qh.schemas.*} properties are bound to a flat map by
 * {@link SchemaNameMappingsConfig}.
 */
@Component
public class SchemaProperties {

    private final Map<String, String> mappings;

    public SchemaProperties(@Qualifier("schemaNameMappings") Map<String, String> mappings) {
        this.mappings = mappings;
    }

    /** Resolves a logical schema name (e.g. "CORE") to its configured physical schema name. */
    public String resolve(String logicalSchemaName) {
        if (logicalSchemaName == null) {
            return null;
        }
        String physical = mappings.get(logicalSchemaName.toLowerCase(Locale.ROOT));
        return physical != null ? physical : logicalSchemaName;
    }
}
