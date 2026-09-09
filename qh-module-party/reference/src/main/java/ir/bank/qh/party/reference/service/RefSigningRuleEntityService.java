package ir.bank.qh.party.reference.service;

import ir.bank.qh.party.reference.entity.RefSigningRuleEntity;
import ir.bank.qh.party.reference.repository.RefSigningRuleEntityRepository;
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
 * Dedicated service for RefSigningRuleEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), matching every other Party service.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class RefSigningRuleEntityService {

    private final RefSigningRuleEntityRepository repository;

    @PersistenceContext(unitName = "party")
    private EntityManager entityManager;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<RefSigningRuleEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public RefSigningRuleEntity findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RefSigningRuleEntity", id));
    }

    public RefSigningRuleEntity create(RefSigningRuleEntity entity) {
        JpaReferenceResolver.resolveManyToOneReferences(entity, entityManager);
        return repository.save(entity);
    }

    public RefSigningRuleEntity update(String id, RefSigningRuleEntity incoming) {
        JpaReferenceResolver.resolveManyToOneReferences(incoming, entityManager);
        RefSigningRuleEntity existing = findById(id);
        BeanUtils.copyProperties(incoming, existing, JpaReferenceResolver.updateIgnoredProperties(RefSigningRuleEntity.class));
        return repository.save(existing);
    }

    public void delete(String id) {
        RefSigningRuleEntity existing = findById(id);
        repository.delete(existing);
    }
}
