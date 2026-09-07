package ir.bank.qh.party.config;

import ir.bank.qh.party.entity.PartyEntity;
import ir.bank.qh.party.entity.PersonEntity;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ValueDeserializer;

/**
 * Lets a REST body reference an existing Party by id alone (e.g.
 * {@code {"party": {"id": 5}}} on PartyAddressEntity/ContactPointEntity/...),
 * even though {@code PartyEntity} is abstract and Jackson otherwise has no
 * way to instantiate it - it silently leaves the field {@code null} instead
 * of failing, which then surfaces much later as a NOT NULL constraint
 * violation on the FK column.
 * <p>
 * Which concrete subtype is built here doesn't matter - {@code id} is the
 * only field ever read off it. {@link ir.bank.qh.common.service.JpaReferenceResolver}
 * replaces this placeholder with a real, correctly-typed managed reference
 * (resolved from the database via {@code EntityManager.getReference}) before
 * the owning entity is saved.
 * <p>
 * Written against Jackson 3 ({@code tools.jackson.*}) - confirmed the active
 * engine at runtime (a deliberately malformed request surfaces
 * {@code tools.jackson.core.exc.StreamReadException}), even though
 * {@code jackson-datatype-hibernate7} additionally pulls a classic Jackson 2
 * jar onto the classpath.
 */
public class PartyEntityReferenceDeserializer extends ValueDeserializer<PartyEntity> {

    @Override
    public PartyEntity deserialize(JsonParser p, DeserializationContext ctxt) throws JacksonException {
        JsonNode node = p.readValueAsTree();
        PartyEntity placeholder = new PersonEntity();
        if (node.hasNonNull("id")) {
            placeholder.setId(node.get("id").asLong());
        }
        return placeholder;
    }
}
