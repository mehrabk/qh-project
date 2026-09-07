package ir.bank.qh.party.service;

import ir.bank.qh.party.entity.PartyNameEntity;
import ir.bank.qh.party.repository.PartyNameEntityRepository;
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
 * Dedicated service for PartyNameEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), matching every other Party service.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class PartyNameEntityService {

    private final PartyNameEntityRepository repository;

    @PersistenceContext(unitName = "party")
    private EntityManager entityManager;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<PartyNameEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public PartyNameEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PartyNameEntity", id));
    }

    public PartyNameEntity create(PartyNameEntity entity) {
        JpaReferenceResolver.resolveManyToOneReferences(entity, entityManager);
        return repository.save(entity);
    }

    public PartyNameEntity update(Long id, PartyNameEntity incoming) {
        JpaReferenceResolver.resolveManyToOneReferences(incoming, entityManager);
        PartyNameEntity existing = findById(id);
        BeanUtils.copyProperties(incoming, existing, JpaReferenceResolver.updateIgnoredProperties(PartyNameEntity.class));
        return repository.save(existing);
    }

    public void delete(Long id) {
        PartyNameEntity existing = findById(id);
        repository.delete(existing);
    }
}
