package ir.bank.qh.party.reference.service;

import ir.bank.qh.party.reference.entity.RefVerificationStatusEntity;
import ir.bank.qh.party.reference.repository.RefVerificationStatusEntityRepository;
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
 * Dedicated service for RefVerificationStatusEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), matching every other Party service.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class RefVerificationStatusEntityService {

    private final RefVerificationStatusEntityRepository repository;

    @PersistenceContext(unitName = "party")
    private EntityManager entityManager;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<RefVerificationStatusEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public RefVerificationStatusEntity findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RefVerificationStatusEntity", id));
    }

    public RefVerificationStatusEntity create(RefVerificationStatusEntity entity) {
        JpaReferenceResolver.resolveManyToOneReferences(entity, entityManager);
        return repository.save(entity);
    }

    public RefVerificationStatusEntity update(String id, RefVerificationStatusEntity incoming) {
        JpaReferenceResolver.resolveManyToOneReferences(incoming, entityManager);
        RefVerificationStatusEntity existing = findById(id);
        BeanUtils.copyProperties(incoming, existing, JpaReferenceResolver.updateIgnoredProperties(RefVerificationStatusEntity.class));
        return repository.save(existing);
    }

    public void delete(String id) {
        RefVerificationStatusEntity existing = findById(id);
        repository.delete(existing);
    }
}
