package ir.bank.qh.party.reference.service;

import ir.bank.qh.party.reference.entity.RefAuditActionEntity;
import ir.bank.qh.party.reference.repository.RefAuditActionEntityRepository;
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
 * Dedicated service for RefAuditActionEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), matching every other Party service.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class RefAuditActionEntityService {

    private final RefAuditActionEntityRepository repository;

    @PersistenceContext(unitName = "party")
    private EntityManager entityManager;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<RefAuditActionEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public RefAuditActionEntity findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RefAuditActionEntity", id));
    }

    public RefAuditActionEntity create(RefAuditActionEntity entity) {
        JpaReferenceResolver.resolveManyToOneReferences(entity, entityManager);
        return repository.save(entity);
    }

    public RefAuditActionEntity update(String id, RefAuditActionEntity incoming) {
        JpaReferenceResolver.resolveManyToOneReferences(incoming, entityManager);
        RefAuditActionEntity existing = findById(id);
        BeanUtils.copyProperties(incoming, existing, JpaReferenceResolver.updateIgnoredProperties(RefAuditActionEntity.class));
        return repository.save(existing);
    }

    public void delete(String id) {
        RefAuditActionEntity existing = findById(id);
        repository.delete(existing);
    }
}
