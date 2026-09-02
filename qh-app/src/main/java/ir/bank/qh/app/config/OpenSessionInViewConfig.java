package ir.bank.qh.app.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Re-implements {@code spring.jpa.open-in-view} (kept lazy associations loadable while a controller
 * serializes the response) for both persistence units - Spring Boot's own Open-Session-In-View
 * interceptor is normally auto-registered per EntityManagerFactory by the single-DataSource JPA
 * autoconfiguration this app excludes (see {@link JpaUnitSupport}), so it has to be done by hand here,
 * once per persistence unit, so it covers both.
 */
@Configuration
public class OpenSessionInViewConfig implements WebMvcConfigurer {

    private final OpenEntityManagerInViewInterceptor productBuilderInterceptor;
    private final OpenEntityManagerInViewInterceptor partyInterceptor;

    public OpenSessionInViewConfig(
            @Qualifier("productBuilderEntityManagerFactory") EntityManagerFactory productBuilderEntityManagerFactory,
            @Qualifier("partyEntityManagerFactory") EntityManagerFactory partyEntityManagerFactory) {
        this.productBuilderInterceptor = new OpenEntityManagerInViewInterceptor();
        this.productBuilderInterceptor.setEntityManagerFactory(productBuilderEntityManagerFactory);
        this.partyInterceptor = new OpenEntityManagerInViewInterceptor();
        this.partyInterceptor.setEntityManagerFactory(partyEntityManagerFactory);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addWebRequestInterceptor(productBuilderInterceptor);
        registry.addWebRequestInterceptor(partyInterceptor);
    }
}
