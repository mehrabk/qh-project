package ir.bank.qh.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Runnable entry point of the modular monolith.
 * <p>
 * Every business module (ir.bank.qh.reference, ir.bank.qh.core,
 * ir.bank.qh.commonrules, ir.bank.qh.deposit, ir.bank.qh.loan) lives under
 * the shared base package ir.bank.qh, so plain component scanning from here
 * ("ir.bank.qh") picks up every module's @Entity, @Repository, @Service and
 * @RestController automatically. To wire in a brand-new module later:
 * <p>
 * 1. Create a new Maven module (e.g. qh-module-card) with its own package
 *    ir.bank.qh.card containing entity/repository/service/web sub-packages,
 *    following the exact same shape as the existing modules.
 * 2. Add it as a &lt;module&gt; in the root pom.xml and as a &lt;dependency&gt;
 *    of qh-app.
 * 3. Nothing else changes here - @SpringBootApplication's component scan
 *    (rooted at ir.bank.qh.app, scanning up to ir.bank.qh) and
 *    {@link EntityScan}/{@link EnableJpaRepositories} below already cover
 *    the whole ir.bank.qh tree.
 */
@SpringBootApplication(scanBasePackages = "ir.bank.qh")
@EntityScan(basePackages = "ir.bank.qh")
@EnableJpaRepositories(basePackages = "ir.bank.qh")
public class QhProductBuilderApplication {

    public static void main(String[] args) {
        SpringApplication.run(QhProductBuilderApplication.class, args);
    }
}
