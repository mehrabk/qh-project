package ir.bank.qh.party.service;

import ir.bank.qh.party.entity.CustomerEntity;
import ir.bank.qh.party.repository.CustomerEntityRepository;
import ir.bank.qh.common.exception.ResourceNotFoundException;
import ir.bank.qh.common.service.JpaReferenceResolver;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Dedicated service for CustomerEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), so this module stays fully
 * self-contained and free to diverge with entity-specific business rules.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class CustomerEntityService {

    private final CustomerEntityRepository repository;

    @PersistenceContext(unitName = "party")
    private EntityManager entityManager;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<CustomerEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public CustomerEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CustomerEntity", id));
    }

    public CustomerEntity create(CustomerEntity entity) {
        JpaReferenceResolver.resolveManyToOneReferences(entity, entityManager);
        return repository.save(entity);
    }

    public CustomerEntity update(Long id, CustomerEntity incoming) {
        JpaReferenceResolver.resolveManyToOneReferences(incoming, entityManager);
        CustomerEntity existing = findById(id);
        BeanUtils.copyProperties(incoming, existing, JpaReferenceResolver.updateIgnoredProperties(CustomerEntity.class));
        return repository.save(existing);
    }

    public void delete(Long id) {
        CustomerEntity existing = findById(id);
        repository.delete(existing);
    }
}
