package ir.bank.qh.party.service;

import ir.bank.qh.party.entity.RefOrgActivityStatusEntity;
import ir.bank.qh.party.repository.RefOrgActivityStatusEntityRepository;
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
 * Dedicated service for RefOrgActivityStatusEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), matching every other Party service.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class RefOrgActivityStatusEntityService {

    private final RefOrgActivityStatusEntityRepository repository;

    @PersistenceContext(unitName = "party")
    private EntityManager entityManager;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<RefOrgActivityStatusEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public RefOrgActivityStatusEntity findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RefOrgActivityStatusEntity", id));
    }

    public RefOrgActivityStatusEntity create(RefOrgActivityStatusEntity entity) {
        JpaReferenceResolver.resolveManyToOneReferences(entity, entityManager);
        return repository.save(entity);
    }

    public RefOrgActivityStatusEntity update(String id, RefOrgActivityStatusEntity incoming) {
        JpaReferenceResolver.resolveManyToOneReferences(incoming, entityManager);
        RefOrgActivityStatusEntity existing = findById(id);
        BeanUtils.copyProperties(incoming, existing, JpaReferenceResolver.updateIgnoredProperties(RefOrgActivityStatusEntity.class));
        return repository.save(existing);
    }

    public void delete(String id) {
        RefOrgActivityStatusEntity existing = findById(id);
        repository.delete(existing);
    }
}
