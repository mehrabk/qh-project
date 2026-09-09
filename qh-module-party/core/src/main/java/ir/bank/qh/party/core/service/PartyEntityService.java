package ir.bank.qh.party.core.service;

import ir.bank.qh.party.core.entity.PartyEntity;
import ir.bank.qh.party.core.repository.PartyEntityRepository;
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
 * Dedicated service for PartyEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), matching every other Party service.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class PartyEntityService {

    private final PartyEntityRepository repository;

    @PersistenceContext(unitName = "party")
    private EntityManager entityManager;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<PartyEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public PartyEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PartyEntity", id));
    }

    public PartyEntity create(PartyEntity entity) {
        JpaReferenceResolver.resolveManyToOneReferences(entity, entityManager);
        return repository.save(entity);
    }

    public PartyEntity update(Long id, PartyEntity incoming) {
        JpaReferenceResolver.resolveManyToOneReferences(incoming, entityManager);
        PartyEntity existing = findById(id);
        BeanUtils.copyProperties(incoming, existing, JpaReferenceResolver.updateIgnoredProperties(PartyEntity.class));
        return repository.save(existing);
    }

    public void delete(Long id) {
        PartyEntity existing = findById(id);
        repository.delete(existing);
    }
}
