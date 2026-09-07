package ir.bank.qh.party.service;

import ir.bank.qh.party.entity.RefIdentityReviewStatusEntity;
import ir.bank.qh.party.repository.RefIdentityReviewStatusEntityRepository;
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
 * Dedicated service for RefIdentityReviewStatusEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), matching every other Party service.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class RefIdentityReviewStatusEntityService {

    private final RefIdentityReviewStatusEntityRepository repository;

    @PersistenceContext(unitName = "party")
    private EntityManager entityManager;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<RefIdentityReviewStatusEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public RefIdentityReviewStatusEntity findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RefIdentityReviewStatusEntity", id));
    }

    public RefIdentityReviewStatusEntity create(RefIdentityReviewStatusEntity entity) {
        JpaReferenceResolver.resolveManyToOneReferences(entity, entityManager);
        return repository.save(entity);
    }

    public RefIdentityReviewStatusEntity update(String id, RefIdentityReviewStatusEntity incoming) {
        JpaReferenceResolver.resolveManyToOneReferences(incoming, entityManager);
        RefIdentityReviewStatusEntity existing = findById(id);
        BeanUtils.copyProperties(incoming, existing, JpaReferenceResolver.updateIgnoredProperties(RefIdentityReviewStatusEntity.class));
        return repository.save(existing);
    }

    public void delete(String id) {
        RefIdentityReviewStatusEntity existing = findById(id);
        repository.delete(existing);
    }
}
