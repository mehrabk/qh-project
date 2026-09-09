package ir.bank.qh.party.reference.service;

import ir.bank.qh.party.reference.entity.RefOwnershipTypeEntity;
import ir.bank.qh.party.reference.repository.RefOwnershipTypeEntityRepository;
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
 * Dedicated service for RefOwnershipTypeEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), matching every other Party service.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class RefOwnershipTypeEntityService {

    private final RefOwnershipTypeEntityRepository repository;

    @PersistenceContext(unitName = "party")
    private EntityManager entityManager;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<RefOwnershipTypeEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public RefOwnershipTypeEntity findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RefOwnershipTypeEntity", id));
    }

    public RefOwnershipTypeEntity create(RefOwnershipTypeEntity entity) {
        JpaReferenceResolver.resolveManyToOneReferences(entity, entityManager);
        return repository.save(entity);
    }

    public RefOwnershipTypeEntity update(String id, RefOwnershipTypeEntity incoming) {
        JpaReferenceResolver.resolveManyToOneReferences(incoming, entityManager);
        RefOwnershipTypeEntity existing = findById(id);
        BeanUtils.copyProperties(incoming, existing, JpaReferenceResolver.updateIgnoredProperties(RefOwnershipTypeEntity.class));
        return repository.save(existing);
    }

    public void delete(String id) {
        RefOwnershipTypeEntity existing = findById(id);
        repository.delete(existing);
    }
}
