package ir.bank.qh.common.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.stereotype.Component;

/**
 * Resolves every entity's logical {@code @Table(schema = "...")} (CORE, REFERENCE, DEPOSIT, ...)
 * to a physical schema name read from {@link SchemaProperties}, so schema names live in
 * {@code application*.yml} rather than being baked into the Java model.
 * <p>
 * Spring Boot auto-detects any {@link org.hibernate.boot.model.naming.PhysicalNamingStrategy}
 * bean in the context and wires it into Hibernate automatically - no explicit
 * {@code hibernate.physical_naming_strategy} property needed.
 */
@Component
public class ConfigurableSchemaNamingStrategy extends PhysicalNamingStrategyStandardImpl {

    private final SchemaProperties schemaProperties;

    public ConfigurableSchemaNamingStrategy(SchemaProperties schemaProperties) {
        this.schemaProperties = schemaProperties;
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier logicalName, JdbcEnvironment jdbcEnvironment) {
        if (logicalName == null) {
            return null;
        }
        String physical = schemaProperties.resolve(logicalName.getText());
        return Identifier.toIdentifier(physical, logicalName.isQuoted());
    }
}
