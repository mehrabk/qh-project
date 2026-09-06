package ir.bank.qh.common.service;

import ir.bank.qh.common.entity.BaseEntity;
import ir.bank.qh.common.exception.ResourceNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Generic CRUD behaviour shared by every entity's service class across every
 * module (core, reference, common-rules, deposit, loan, ...). Concrete
 * services only need to supply the repository and an entity display-name,
 * and may override {@link #beforeCreate(BaseEntity)} /
 * {@link #beforeUpdate(BaseEntity, BaseEntity)} to enforce
 * the CHECK-constraint-equivalent business rules from the original data
 * model (e.g. age ranges, date ranges, mandatory flags).
 * <p>
 * {@code create}/{@code update} run every {@code @ManyToOne} reference on the
 * incoming entity through {@link JpaReferenceResolver} first: a REST body's
 * associations arrive as transient "id-only" objects, and every entity here
 * carries a {@code @JsonIgnore}d {@code @Version} field, so Hibernate cannot
 * otherwise tell those references apart from a brand-new, unsaved row.
 * {@code update} additionally never hands the client's own deserialized
 * object to {@code save()} - since its {@code version} is always null, that
 * would fail the exact same way - and instead copies the incoming field
 * values onto the entity freshly loaded by {@link #findById}, which already
 * carries the real, current version.
 *
 * @param <T> the JPA entity type, must extend {@link BaseEntity}
 */
public abstract class AbstractCrudService<T extends BaseEntity> {

    @PersistenceContext(unitName = "productBuilder")
    private EntityManager entityManager;

    protected abstract JpaRepository<T, Long> repository();

    protected abstract String entityName();

    /** Hook for subclasses: validate/mutate before first persist. Default: no-op. */
    protected void beforeCreate(T entity) {
    }

    /** Hook for subclasses: validate/mutate before merging changes into the existing row. Default: no-op. */
    protected void beforeUpdate(T existing, T incoming) {
    }

    public List<T> findAll() {
        return repository().findAll();
    }

    public T findById(Long id) {
        return repository().findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(entityName(), id));
    }

    public T create(T entity) {
        JpaReferenceResolver.resolveManyToOneReferences(entity, entityManager);
        beforeCreate(entity);
        return repository().save(entity);
    }

    public T update(Long id, T incoming) {
        JpaReferenceResolver.resolveManyToOneReferences(incoming, entityManager);
        T existing = findById(id);
        beforeUpdate(existing, incoming);
        BeanUtils.copyProperties(incoming, existing, JpaReferenceResolver.updateIgnoredProperties(existing.getClass()));
        return repository().save(existing);
    }

    public void delete(Long id) {
        T existing = findById(id);
        repository().delete(existing);
    }
}
