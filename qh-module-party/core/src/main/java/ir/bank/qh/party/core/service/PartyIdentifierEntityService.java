package ir.bank.qh.party.core.service;

import ir.bank.qh.party.core.entity.PartyIdentifierEntity;
import ir.bank.qh.party.core.repository.PartyIdentifierEntityRepository;
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
 * Dedicated service for PartyIdentifierEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), matching every other Party service.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class PartyIdentifierEntityService {

    private final PartyIdentifierEntityRepository repository;

    @PersistenceContext(unitName = "party")
    private EntityManager entityManager;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<PartyIdentifierEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public PartyIdentifierEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PartyIdentifierEntity", id));
    }

    public PartyIdentifierEntity create(PartyIdentifierEntity entity) {
        JpaReferenceResolver.resolveManyToOneReferences(entity, entityManager);
        return repository.save(entity);
    }

    public PartyIdentifierEntity update(Long id, PartyIdentifierEntity incoming) {
        JpaReferenceResolver.resolveManyToOneReferences(incoming, entityManager);
        PartyIdentifierEntity existing = findById(id);
        BeanUtils.copyProperties(incoming, existing, JpaReferenceResolver.updateIgnoredProperties(PartyIdentifierEntity.class));
        return repository.save(existing);
    }

    public void delete(Long id) {
        PartyIdentifierEntity existing = findById(id);
        repository.delete(existing);
    }
}
