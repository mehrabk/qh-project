package ir.bank.qh.common.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Resolves a client-supplied "reference by id" association (e.g. a REST body
 * of {@code {"product": {"id": 5}}}, deserialized into a transient
 * {@code Product} instance with only its {@code id} field set) into a real
 * Hibernate-managed reference via {@link EntityManager#getReference} before a
 * save/persist.
 * <p>
 * This is necessary because every entity in this project carries a
 * {@code @Version} field for optimistic locking (see {@code BaseEntity}), and
 * that field is deliberately {@code @JsonIgnore}d so REST clients can never
 * read or spoof it. Without this resolution step, Hibernate receives an
 * association target with a non-null id but a null version and cannot tell
 * whether it is transient or detached, failing with
 * "Detached entity with generated id '...' has an uninitialized version
 * value". {@link EntityManager#getReference} sidesteps the check entirely by
 * handing Hibernate a proxy it created and trusts itself.
 */
public final class JpaReferenceResolver {

    private JpaReferenceResolver() {
    }

    /**
     * Walks every {@code @ManyToOne} (and owning-side {@code @OneToOne}) field
     * of {@code entity}, and replaces any non-null reference that carries a
     * non-null {@code id} with a managed reference obtained from
     * {@code entityManager}. Fields left null, or referencing a not-yet-saved
     * (id == null) instance, are left untouched.
     */
    public static void resolveManyToOneReferences(Object entity, EntityManager entityManager) {
        if (entity == null) {
            return;
        }
        ReflectionUtils.doWithFields(entity.getClass(), field -> {
            if (!isOwningAssociation(field)) {
                return;
            }
            ReflectionUtils.makeAccessible(field);
            Object reference = ReflectionUtils.getField(field, entity);
            if (reference == null) {
                return;
            }
            Field idField = ReflectionUtils.findField(reference.getClass(), "id");
            if (idField == null) {
                return;
            }
            ReflectionUtils.makeAccessible(idField);
            Object id = ReflectionUtils.getField(idField, reference);
            if (id == null) {
                return;
            }
            Object managedReference = entityManager.getReference(field.getType(), id);
            ReflectionUtils.setField(field, entity, managedReference);
        });
    }

    private static boolean isOwningAssociation(Field field) {
        if (field.isAnnotationPresent(ManyToOne.class)) {
            return true;
        }
        OneToOne oneToOne = field.getAnnotation(OneToOne.class);
        return oneToOne != null && oneToOne.mappedBy().isEmpty();
    }

    /**
     * Names of every {@code @OneToMany}/{@code @ManyToMany} field, plus every
     * inverse-side ({@code mappedBy} non-empty) {@code @OneToOne} field, on
     * {@code entityClass} and its superclasses.
     * <p>
     * These are the fields a plain {@code BeanUtils.copyProperties(incoming,
     * existing, ...)} during an update must never touch: Hibernate manages
     * their collection/reference instances itself (dirty-checking,
     * orphan-removal), and overwriting one with a fresh, disconnected object
     * from the client's deserialized JSON - typically an empty collection,
     * since these back-references are usually never sent by REST clients -
     * makes Hibernate think every previously-associated row was just removed
     * from it, and orphanRemoval then tries to delete them all, or Hibernate
     * simply refuses the swap outright ("collection with orphan deletion was
     * no longer referenced by the owning entity instance").
     */
    public static String[] nonOwningRelationFieldNames(Class<?> entityClass) {
        List<String> names = new ArrayList<>();
        ReflectionUtils.doWithFields(entityClass, field -> names.add(field.getName()), field ->
                field.isAnnotationPresent(OneToMany.class)
                        || field.isAnnotationPresent(ManyToMany.class)
                        || (field.isAnnotationPresent(OneToOne.class) && !field.getAnnotation(OneToOne.class).mappedBy().isEmpty()));
        return names.toArray(new String[0]);
    }

    /**
     * {@link #nonOwningRelationFieldNames} plus {@code id}/{@code version}/
     * {@code createdBy}/{@code createdOn} - the full property-name list an
     * update's {@code BeanUtils.copyProperties(incoming, existing, ...)} must
     * ignore so it only ever touches an entity's own plain/reference fields.
     */
    public static String[] updateIgnoredProperties(Class<?> entityClass) {
        String[] relations = nonOwningRelationFieldNames(entityClass);
        String[] result = Arrays.copyOf(relations, relations.length + 4);
        result[relations.length] = "id";
        result[relations.length + 1] = "version";
        result[relations.length + 2] = "createdBy";
        result[relations.length + 3] = "createdOn";
        return result;
    }
}
