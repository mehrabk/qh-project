package ir.bank.qh.app.config;

import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.hibernate.autoconfigure.HibernateProperties;
import org.springframework.boot.hibernate.autoconfigure.HibernatePropertiesCustomizer;
import org.springframework.boot.hibernate.autoconfigure.HibernateSettings;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.jpa.autoconfigure.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Manual replacement for the {@code HibernateJpaAutoConfiguration} Spring Boot would normally wire up
 * for a single {@code DataSource} - not applicable here since this app runs two independent persistence
 * units (Product Builder, Party; see {@link ProductBuilderPersistenceConfig}/{@link PartyPersistenceConfig})
 * on two separate databases.
 * <p>
 * {@code spring.jpa.*} / {@code spring.jpa.hibernate.*} are bound exactly as usual (both persistence
 * units share the same Hibernate behaviour - ddl-auto, naming strategy, tenant resolver, etc.) via
 * {@link JpaProperties}/{@link HibernateProperties}, and the {@link EntityManagerFactoryBuilder} built
 * here is reusable across both persistence units: {@code .dataSource(...)} returns a fresh builder each
 * time without mutating shared state.
 */
@Configuration
@EnableConfigurationProperties({JpaProperties.class, HibernateProperties.class})
public class JpaUnitSupport {

    @Bean
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder(
            JpaProperties jpaProperties,
            HibernateProperties hibernateProperties,
            ObjectProvider<PhysicalNamingStrategy> physicalNamingStrategy,
            ObjectProvider<ImplicitNamingStrategy> implicitNamingStrategy,
            ObjectProvider<HibernatePropertiesCustomizer> hibernatePropertiesCustomizers) {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(jpaProperties.isShowSql());
        vendorAdapter.setGenerateDdl(jpaProperties.isGenerateDdl());
        vendorAdapter.setDatabasePlatform(jpaProperties.getDatabasePlatform());

        List<HibernatePropertiesCustomizer> customizers = new ArrayList<>();
        PhysicalNamingStrategy pns = physicalNamingStrategy.getIfAvailable();
        ImplicitNamingStrategy ins = implicitNamingStrategy.getIfAvailable();
        if (pns != null || ins != null) {
            customizers.add(properties -> {
                if (pns != null) {
                    properties.put("hibernate.physical_naming_strategy", pns);
                }
                if (ins != null) {
                    properties.put("hibernate.implicit_naming_strategy", ins);
                }
            });
        }
        hibernatePropertiesCustomizers.forEach(customizers::add);

        HibernateSettings settings = new HibernateSettings()
                .ddlAuto(() -> null)
                .hibernatePropertiesCustomizers(customizers);

        Function<DataSource, Map<String, ?>> jpaPropertiesFactory = dataSource ->
                hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), settings);

        return new EntityManagerFactoryBuilder(vendorAdapter, jpaPropertiesFactory, null);
    }
}
