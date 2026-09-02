package ir.bank.qh.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceInitializationAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceTransactionManagerAutoConfiguration;

/**
 * Runnable entry point of the modular monolith.
 * <p>
 * This app runs two independent persistence units on two independent databases - Product Builder
 * (reference/core/commonrules/deposit/loan, one bounded context sharing the PRODUCTBUILDER schema)
 * and Party (its own PARTY schema) - wired explicitly in {@code ir.bank.qh.app.config}
 * ({@link ir.bank.qh.app.config.ProductBuilderPersistenceConfig},
 * {@link ir.bank.qh.app.config.PartyPersistenceConfig}). Spring Boot's usual single-DataSource JPA
 * auto-configuration is excluded below because it can only ever wire up one
 * DataSource/EntityManagerFactory/TransactionManager - see qh-app/config's javadoc and README's
 * "Domain vs Schema" section for the reasoning.
 * <p>
 * Everything else (services, controllers) still lives under the shared base package ir.bank.qh, so
 * plain component scanning from here ("ir.bank.qh") picks up every module's @Service and
 * @RestController automatically. To wire in a brand-new module later:
 * <p>
 * 1. Create a new Maven module (e.g. qh-module-card) with its own package ir.bank.qh.card containing
 *    entity/repository/service/web sub-packages, following the exact same shape as the existing
 *    modules.
 * 2. Add it as a &lt;module&gt; in the root pom.xml and as a &lt;dependency&gt; of qh-app.
 * 3. Add its entity/repository packages to whichever persistence unit it belongs to (Product Builder,
 *    Party, or a brand-new one) in {@code ir.bank.qh.app.config}.
 */
@SpringBootApplication(
        scanBasePackages = "ir.bank.qh",
        exclude = {
                DataSourceAutoConfiguration.class,
                DataSourceTransactionManagerAutoConfiguration.class,
                DataSourceInitializationAutoConfiguration.class,
                HibernateJpaAutoConfiguration.class
        })
public class QhProductBuilderApplication {

    public static void main(String[] args) {
        SpringApplication.run(QhProductBuilderApplication.class, args);
    }
}
