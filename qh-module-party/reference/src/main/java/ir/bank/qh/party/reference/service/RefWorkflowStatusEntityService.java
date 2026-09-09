package ir.bank.qh.party.reference.service;

import ir.bank.qh.party.reference.entity.RefWorkflowStatusEntity;
import ir.bank.qh.party.reference.repository.RefWorkflowStatusEntityRepository;
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
 * Dedicated service for RefWorkflowStatusEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), matching every other Party service.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class RefWorkflowStatusEntityService {

    private final RefWorkflowStatusEntityRepository repository;

    @PersistenceContext(unitName = "party")
    private EntityManager entityManager;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<RefWorkflowStatusEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public RefWorkflowStatusEntity findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RefWorkflowStatusEntity", id));
    }

    public RefWorkflowStatusEntity create(RefWorkflowStatusEntity entity) {
        JpaReferenceResolver.resolveManyToOneReferences(entity, entityManager);
        return repository.save(entity);
    }

    public RefWorkflowStatusEntity update(String id, RefWorkflowStatusEntity incoming) {
        JpaReferenceResolver.resolveManyToOneReferences(incoming, entityManager);
        RefWorkflowStatusEntity existing = findById(id);
        BeanUtils.copyProperties(incoming, existing, JpaReferenceResolver.updateIgnoredProperties(RefWorkflowStatusEntity.class));
        return repository.save(existing);
    }

    public void delete(String id) {
        RefWorkflowStatusEntity existing = findById(id);
        repository.delete(existing);
    }
}
