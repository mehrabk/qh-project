package ir.bank.qh.party.service;

import ir.bank.qh.party.entity.RefIdentityReviewReasonEntity;
import ir.bank.qh.party.repository.RefIdentityReviewReasonEntityRepository;
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
 * Dedicated service for RefIdentityReviewReasonEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), matching every other Party service.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class RefIdentityReviewReasonEntityService {

    private final RefIdentityReviewReasonEntityRepository repository;

    @PersistenceContext(unitName = "party")
    private EntityManager entityManager;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<RefIdentityReviewReasonEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public RefIdentityReviewReasonEntity findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RefIdentityReviewReasonEntity", id));
    }

    public RefIdentityReviewReasonEntity create(RefIdentityReviewReasonEntity entity) {
        JpaReferenceResolver.resolveManyToOneReferences(entity, entityManager);
        return repository.save(entity);
    }

    public RefIdentityReviewReasonEntity update(String id, RefIdentityReviewReasonEntity incoming) {
        JpaReferenceResolver.resolveManyToOneReferences(incoming, entityManager);
        RefIdentityReviewReasonEntity existing = findById(id);
        BeanUtils.copyProperties(incoming, existing, JpaReferenceResolver.updateIgnoredProperties(RefIdentityReviewReasonEntity.class));
        return repository.save(existing);
    }

    public void delete(String id) {
        RefIdentityReviewReasonEntity existing = findById(id);
        repository.delete(existing);
    }
}
