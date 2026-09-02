package ir.bank.qh.app.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.boot.jdbc.init.DataSourceScriptDatabaseInitializer;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.sql.init.DatabaseInitializationMode;
import org.springframework.boot.sql.init.DatabaseInitializationSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Wires the "Product Builder" persistence unit - the single {@code PRODUCTBUILDER} schema shared by
 * reference/core/commonrules/deposit/loan (see README's "Domain vs Schema" note: these five packages
 * are one bounded context, not five). Its own {@code DataSource}/{@code EntityManagerFactory}/
 * {@code PlatformTransactionManager} keep it fully isolated from the Party persistence unit
 * ({@link PartyPersistenceConfig}), on a separate database - swap {@code qh.datasources.productbuilder.*}
 * in application*.yml to point this at a different server (e.g. Oracle) without touching any code.
 */
@Configuration
@EnableJpaRepositories(
        basePackages = "ir.bank.qh.productbuilder",
        entityManagerFactoryRef = "productBuilderEntityManagerFactory",
        transactionManagerRef = "productBuilderTransactionManager")
public class ProductBuilderPersistenceConfig {

    @Bean
    @ConfigurationProperties("qh.datasources.productbuilder")
    public DataSourceProperties productBuilderDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource productBuilderDataSource(
            @Qualifier("productBuilderDataSourceProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean productBuilderEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("productBuilderDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource)
                .packages("ir.bank.qh.productbuilder")
                .persistenceUnit("productBuilder")
                .build();
    }

    @Bean
    public PlatformTransactionManager productBuilderTransactionManager(
            @Qualifier("productBuilderEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    /**
     * Runs after {@code productBuilderEntityManagerFactory} (declared as a dependency below) so the
     * ddl-auto schema/table creation has already happened - equivalent to the single-DataSource
     * {@code spring.jpa.defer-datasource-initialization} setting, which only applies to Boot's own
     * auto-configured initializer.
     */
    @Bean
    public DataSourceScriptDatabaseInitializer productBuilderDatabaseInitializer(
            @Qualifier("productBuilderDataSource") DataSource dataSource,
            @Qualifier("productBuilderEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        DatabaseInitializationSettings settings = new DatabaseInitializationSettings();
        settings.setDataLocations(List.of("classpath:data-product.sql"));
        // EMBEDDED (not ALWAYS): only seeds against an in-memory database (H2/HSQL/Derby). A real
        // deployment (e.g. Oracle) is never "embedded", so this demo data is skipped automatically -
        // no per-profile override needed.
        settings.setMode(DatabaseInitializationMode.EMBEDDED);
        settings.setEncoding(StandardCharsets.UTF_8);
        return new DataSourceScriptDatabaseInitializer(dataSource, settings);
    }
}
