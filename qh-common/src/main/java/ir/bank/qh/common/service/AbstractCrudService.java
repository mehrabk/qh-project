package ir.bank.qh.common.service;

import ir.bank.qh.common.entity.BaseAuditableEntity;
import ir.bank.qh.common.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Generic CRUD behaviour shared by every entity's service class across every
 * module (core, reference, common-rules, deposit, loan, ...). Concrete
 * services only need to supply the repository and an entity display-name,
 * and may override {@link #beforeCreate(BaseAuditableEntity)} /
 * {@link #beforeUpdate(BaseAuditableEntity, BaseAuditableEntity)} to enforce
 * the CHECK-constraint-equivalent business rules from the original data
 * model (e.g. age ranges, date ranges, mandatory flags).
 *
 * @param <T> the JPA entity type, must extend {@link BaseAuditableEntity}
 */
public abstract class AbstractCrudService<T extends BaseAuditableEntity> {

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
        beforeCreate(entity);
        return repository().save(entity);
    }

    public T update(Long id, T incoming) {
        T existing = findById(id);
        beforeUpdate(existing, incoming);
        return repository().save(incoming);
    }

    public void delete(Long id) {
        T existing = findById(id);
        repository().delete(existing);
    }
}
