package ir.bank.qh.party.service;

import ir.bank.qh.party.entity.AuditFieldChangeEntity;
import ir.bank.qh.party.repository.AuditFieldChangeEntityRepository;
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
 * Dedicated service for AuditFieldChangeEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), matching every other Party service.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class AuditFieldChangeEntityService {

    private final AuditFieldChangeEntityRepository repository;

    @PersistenceContext(unitName = "party")
    private EntityManager entityManager;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<AuditFieldChangeEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public AuditFieldChangeEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AuditFieldChangeEntity", id));
    }

    public AuditFieldChangeEntity create(AuditFieldChangeEntity entity) {
        JpaReferenceResolver.resolveManyToOneReferences(entity, entityManager);
        return repository.save(entity);
    }

    public AuditFieldChangeEntity update(Long id, AuditFieldChangeEntity incoming) {
        JpaReferenceResolver.resolveManyToOneReferences(incoming, entityManager);
        AuditFieldChangeEntity existing = findById(id);
        BeanUtils.copyProperties(incoming, existing, JpaReferenceResolver.updateIgnoredProperties(AuditFieldChangeEntity.class));
        return repository.save(existing);
    }

    public void delete(Long id) {
        AuditFieldChangeEntity existing = findById(id);
        repository.delete(existing);
    }
}
