package ir.bank.qh.party.core.service;

import ir.bank.qh.party.core.entity.AddressEntity;
import ir.bank.qh.party.core.repository.AddressEntityRepository;
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
 * Dedicated service for AddressEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), matching every other Party service.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class AddressEntityService {

    private final AddressEntityRepository repository;

    @PersistenceContext(unitName = "party")
    private EntityManager entityManager;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<AddressEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public AddressEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AddressEntity", id));
    }

    public AddressEntity create(AddressEntity entity) {
        JpaReferenceResolver.resolveManyToOneReferences(entity, entityManager);
        return repository.save(entity);
    }

    public AddressEntity update(Long id, AddressEntity incoming) {
        JpaReferenceResolver.resolveManyToOneReferences(incoming, entityManager);
        AddressEntity existing = findById(id);
        BeanUtils.copyProperties(incoming, existing, JpaReferenceResolver.updateIgnoredProperties(AddressEntity.class));
        return repository.save(existing);
    }

    public void delete(Long id) {
        AddressEntity existing = findById(id);
        repository.delete(existing);
    }
}
