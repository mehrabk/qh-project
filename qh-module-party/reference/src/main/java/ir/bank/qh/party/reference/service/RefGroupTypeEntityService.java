package ir.bank.qh.party.reference.service;

import ir.bank.qh.party.reference.entity.RefGroupTypeEntity;
import ir.bank.qh.party.reference.repository.RefGroupTypeEntityRepository;
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
 * Dedicated service for RefGroupTypeEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), matching every other Party service.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class RefGroupTypeEntityService {

    private final RefGroupTypeEntityRepository repository;

    @PersistenceContext(unitName = "party")
    private EntityManager entityManager;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<RefGroupTypeEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public RefGroupTypeEntity findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RefGroupTypeEntity", id));
    }

    public RefGroupTypeEntity create(RefGroupTypeEntity entity) {
        JpaReferenceResolver.resolveManyToOneReferences(entity, entityManager);
        return repository.save(entity);
    }

    public RefGroupTypeEntity update(String id, RefGroupTypeEntity incoming) {
        JpaReferenceResolver.resolveManyToOneReferences(incoming, entityManager);
        RefGroupTypeEntity existing = findById(id);
        BeanUtils.copyProperties(incoming, existing, JpaReferenceResolver.updateIgnoredProperties(RefGroupTypeEntity.class));
        return repository.save(existing);
    }

    public void delete(String id) {
        RefGroupTypeEntity existing = findById(id);
        repository.delete(existing);
    }
}
