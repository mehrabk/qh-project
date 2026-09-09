package ir.bank.qh.party.reference.service;

import ir.bank.qh.party.reference.entity.RefChannelEntity;
import ir.bank.qh.party.reference.repository.RefChannelEntityRepository;
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
 * Dedicated service for RefChannelEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), matching every other Party service.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class RefChannelEntityService {

    private final RefChannelEntityRepository repository;

    @PersistenceContext(unitName = "party")
    private EntityManager entityManager;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<RefChannelEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public RefChannelEntity findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RefChannelEntity", id));
    }

    public RefChannelEntity create(RefChannelEntity entity) {
        JpaReferenceResolver.resolveManyToOneReferences(entity, entityManager);
        return repository.save(entity);
    }

    public RefChannelEntity update(String id, RefChannelEntity incoming) {
        JpaReferenceResolver.resolveManyToOneReferences(incoming, entityManager);
        RefChannelEntity existing = findById(id);
        BeanUtils.copyProperties(incoming, existing, JpaReferenceResolver.updateIgnoredProperties(RefChannelEntity.class));
        return repository.save(existing);
    }

    public void delete(String id) {
        RefChannelEntity existing = findById(id);
        repository.delete(existing);
    }
}
