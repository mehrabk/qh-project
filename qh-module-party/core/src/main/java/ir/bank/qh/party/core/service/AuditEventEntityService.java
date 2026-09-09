package ir.bank.qh.party.core.service;

import ir.bank.qh.party.core.entity.AuditEventEntity;
import ir.bank.qh.party.core.repository.AuditEventEntityRepository;
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
 * Dedicated service for AuditEventEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), matching every other Party service.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class AuditEventEntityService {

    private final AuditEventEntityRepository repository;

    @PersistenceContext(unitName = "party")
    private EntityManager entityManager;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<AuditEventEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public AuditEventEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AuditEventEntity", id));
    }

    public AuditEventEntity create(AuditEventEntity entity) {
        JpaReferenceResolver.resolveManyToOneReferences(entity, entityManager);
        return repository.save(entity);
    }

    public AuditEventEntity update(Long id, AuditEventEntity incoming) {
        JpaReferenceResolver.resolveManyToOneReferences(incoming, entityManager);
        AuditEventEntity existing = findById(id);
        BeanUtils.copyProperties(incoming, existing, JpaReferenceResolver.updateIgnoredProperties(AuditEventEntity.class));
        return repository.save(existing);
    }

    public void delete(Long id) {
        AuditEventEntity existing = findById(id);
        repository.delete(existing);
    }
}
