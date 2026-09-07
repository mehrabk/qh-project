package ir.bank.qh.party.config;

import ir.bank.qh.party.entity.PartyEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;

import java.util.List;
import java.util.ListIterator;

/**
 * Registers {@link PartyEntityReferenceDeserializer} directly onto the
 * {@code JacksonJsonHttpMessageConverter} Spring MVC actually uses for every
 * REST request body.
 * <p>
 * A plain {@code @Bean JacksonModule} is not enough here: this Boot version's
 * {@code AbstractJacksonHttpMessageConverter} discovers its modules through
 * its own internal, statically-cached {@code initModules()} (classpath
 * service-loading), not through Spring's DI container, so a Spring-managed
 * module bean is simply never consulted by it. Jackson 3's {@code ObjectMapper}
 * is also immutable once built (no {@code registerModule()}), so the fix is
 * to {@code rebuild()} the converter's existing mapper with our module added
 * and swap in a new converter instance carrying it, via
 * {@link WebMvcConfigurer#extendMessageConverters}, which runs after Spring
 * Boot has assembled the real converter list.
 */
@Configuration
public class PartyJacksonConfig implements WebMvcConfigurer {

    @Bean
    public SimpleModule partyEntityReferenceModule() {
        SimpleModule module = new SimpleModule("PartyEntityReferenceModule");
        module.addDeserializer(PartyEntity.class, new PartyEntityReferenceDeserializer());
        return module;
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        ListIterator<HttpMessageConverter<?>> it = converters.listIterator();
        while (it.hasNext()) {
            HttpMessageConverter<?> converter = it.next();
            if (converter instanceof JacksonJsonHttpMessageConverter jacksonConverter) {
                JsonMapper existing = jacksonConverter.getMapper();
                JsonMapper rebuilt = existing.rebuild().addModule(partyEntityReferenceModule()).build();
                it.set(new JacksonJsonHttpMessageConverter(rebuilt));
            }
        }
    }
}
