package ir.bank.qh.party.service;

import ir.bank.qh.party.entity.PartyIdentityReviewEntity;
import ir.bank.qh.party.repository.PartyIdentityReviewEntityRepository;
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
 * Dedicated service for PartyIdentityReviewEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), matching every other Party service.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class PartyIdentityReviewEntityService {

    private final PartyIdentityReviewEntityRepository repository;

    @PersistenceContext(unitName = "party")
    private EntityManager entityManager;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<PartyIdentityReviewEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public PartyIdentityReviewEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PartyIdentityReviewEntity", id));
    }

    public PartyIdentityReviewEntity create(PartyIdentityReviewEntity entity) {
        JpaReferenceResolver.resolveManyToOneReferences(entity, entityManager);
        return repository.save(entity);
    }

    public PartyIdentityReviewEntity update(Long id, PartyIdentityReviewEntity incoming) {
        JpaReferenceResolver.resolveManyToOneReferences(incoming, entityManager);
        PartyIdentityReviewEntity existing = findById(id);
        BeanUtils.copyProperties(incoming, existing, JpaReferenceResolver.updateIgnoredProperties(PartyIdentityReviewEntity.class));
        return repository.save(existing);
    }

    public void delete(Long id) {
        PartyIdentityReviewEntity existing = findById(id);
        repository.delete(existing);
    }
}
