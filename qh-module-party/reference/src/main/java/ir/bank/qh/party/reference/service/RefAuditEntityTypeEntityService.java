package ir.bank.qh.party.reference.service;

import ir.bank.qh.party.reference.entity.RefAuditEntityTypeEntity;
import ir.bank.qh.party.reference.repository.RefAuditEntityTypeEntityRepository;
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
 * Dedicated service for RefAuditEntityTypeEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), matching every other Party service.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class RefAuditEntityTypeEntityService {

    private final RefAuditEntityTypeEntityRepository repository;

    @PersistenceContext(unitName = "party")
    private EntityManager entityManager;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<RefAuditEntityTypeEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public RefAuditEntityTypeEntity findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RefAuditEntityTypeEntity", id));
    }

    public RefAuditEntityTypeEntity create(RefAuditEntityTypeEntity entity) {
        JpaReferenceResolver.resolveManyToOneReferences(entity, entityManager);
        return repository.save(entity);
    }

    public RefAuditEntityTypeEntity update(String id, RefAuditEntityTypeEntity incoming) {
        JpaReferenceResolver.resolveManyToOneReferences(incoming, entityManager);
        RefAuditEntityTypeEntity existing = findById(id);
        BeanUtils.copyProperties(incoming, existing, JpaReferenceResolver.updateIgnoredProperties(RefAuditEntityTypeEntity.class));
        return repository.save(existing);
    }

    public void delete(String id) {
        RefAuditEntityTypeEntity existing = findById(id);
        repository.delete(existing);
    }
}
