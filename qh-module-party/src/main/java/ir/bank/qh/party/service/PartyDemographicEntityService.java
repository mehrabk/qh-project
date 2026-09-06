package ir.bank.qh.party.service;

import ir.bank.qh.party.entity.PartyDemographicEntity;
import ir.bank.qh.party.repository.PartyDemographicEntityRepository;
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
 * Dedicated service for PartyDemographicEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), so this module stays fully
 * self-contained and free to diverge with entity-specific business rules.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class PartyDemographicEntityService {

    private final PartyDemographicEntityRepository repository;

    @PersistenceContext(unitName = "party")
    private EntityManager entityManager;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<PartyDemographicEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public PartyDemographicEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PartyDemographicEntity", id));
    }

    public PartyDemographicEntity create(PartyDemographicEntity entity) {
        JpaReferenceResolver.resolveManyToOneReferences(entity, entityManager);
        return repository.save(entity);
    }

    public PartyDemographicEntity update(Long id, PartyDemographicEntity incoming) {
        JpaReferenceResolver.resolveManyToOneReferences(incoming, entityManager);
        PartyDemographicEntity existing = findById(id);
        BeanUtils.copyProperties(incoming, existing, JpaReferenceResolver.updateIgnoredProperties(PartyDemographicEntity.class));
        return repository.save(existing);
    }

    public void delete(Long id) {
        PartyDemographicEntity existing = findById(id);
        repository.delete(existing);
    }
}
