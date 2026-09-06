package ir.bank.qh.party.service;

import ir.bank.qh.party.entity.PartyRoleEntity;
import ir.bank.qh.party.repository.PartyRoleEntityRepository;
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
 * Dedicated service for PartyRoleEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), so this module stays fully
 * self-contained and free to diverge with entity-specific business rules.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class PartyRoleEntityService {

    private final PartyRoleEntityRepository repository;

    @PersistenceContext(unitName = "party")
    private EntityManager entityManager;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<PartyRoleEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public PartyRoleEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PartyRoleEntity", id));
    }

    public PartyRoleEntity create(PartyRoleEntity entity) {
        JpaReferenceResolver.resolveManyToOneReferences(entity, entityManager);
        return repository.save(entity);
    }

    public PartyRoleEntity update(Long id, PartyRoleEntity incoming) {
        JpaReferenceResolver.resolveManyToOneReferences(incoming, entityManager);
        PartyRoleEntity existing = findById(id);
        BeanUtils.copyProperties(incoming, existing, JpaReferenceResolver.updateIgnoredProperties(PartyRoleEntity.class));
        return repository.save(existing);
    }

    public void delete(Long id) {
        PartyRoleEntity existing = findById(id);
        repository.delete(existing);
    }
}
