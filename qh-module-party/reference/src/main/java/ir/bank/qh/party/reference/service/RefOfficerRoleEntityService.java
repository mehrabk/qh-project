package ir.bank.qh.party.reference.service;

import ir.bank.qh.party.reference.entity.RefOfficerRoleEntity;
import ir.bank.qh.party.reference.repository.RefOfficerRoleEntityRepository;
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
 * Dedicated service for RefOfficerRoleEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), matching every other Party service.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class RefOfficerRoleEntityService {

    private final RefOfficerRoleEntityRepository repository;

    @PersistenceContext(unitName = "party")
    private EntityManager entityManager;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<RefOfficerRoleEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public RefOfficerRoleEntity findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RefOfficerRoleEntity", id));
    }

    public RefOfficerRoleEntity create(RefOfficerRoleEntity entity) {
        JpaReferenceResolver.resolveManyToOneReferences(entity, entityManager);
        return repository.save(entity);
    }

    public RefOfficerRoleEntity update(String id, RefOfficerRoleEntity incoming) {
        JpaReferenceResolver.resolveManyToOneReferences(incoming, entityManager);
        RefOfficerRoleEntity existing = findById(id);
        BeanUtils.copyProperties(incoming, existing, JpaReferenceResolver.updateIgnoredProperties(RefOfficerRoleEntity.class));
        return repository.save(existing);
    }

    public void delete(String id) {
        RefOfficerRoleEntity existing = findById(id);
        repository.delete(existing);
    }
}
