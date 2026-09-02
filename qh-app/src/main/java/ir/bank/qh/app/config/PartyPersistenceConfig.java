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
 * Wires the "Party" persistence unit - its own {@code PARTY} schema on its own database, the one
 * boundary in this system actually designed to move onto a separate server (see
 * {@link ProductBuilderPersistenceConfig}'s note on the real bounded-context boundary).
 */
@Configuration
@EnableJpaRepositories(
        basePackages = "ir.bank.qh.party.repository",
        entityManagerFactoryRef = "partyEntityManagerFactory",
        transactionManagerRef = "partyTransactionManager")
public class PartyPersistenceConfig {

    @Bean
    @ConfigurationProperties("qh.datasources.party")
    public DataSourceProperties partyDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource partyDataSource(
            @Qualifier("partyDataSourceProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean partyEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("partyDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource)
                .packages("ir.bank.qh.party.entity")
                .persistenceUnit("party")
                .build();
    }

    @Bean
    public PlatformTransactionManager partyTransactionManager(
            @Qualifier("partyEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public DataSourceScriptDatabaseInitializer partyDatabaseInitializer(
            @Qualifier("partyDataSource") DataSource dataSource,
            @Qualifier("partyEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        DatabaseInitializationSettings settings = new DatabaseInitializationSettings();
        settings.setDataLocations(List.of("classpath:data-party.sql"));
        // EMBEDDED (not ALWAYS): only seeds against an in-memory database - see the identical note
        // in ProductBuilderPersistenceConfig.
        settings.setMode(DatabaseInitializationMode.EMBEDDED);
        settings.setEncoding(StandardCharsets.UTF_8);
        return new DataSourceScriptDatabaseInitializer(dataSource, settings);
    }
}
