package ir.bank.qh.party.reference.service;

import ir.bank.qh.party.reference.entity.RefGroupMemberRoleEntity;
import ir.bank.qh.party.reference.repository.RefGroupMemberRoleEntityRepository;
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
 * Dedicated service for RefGroupMemberRoleEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), matching every other Party service.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class RefGroupMemberRoleEntityService {

    private final RefGroupMemberRoleEntityRepository repository;

    @PersistenceContext(unitName = "party")
    private EntityManager entityManager;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<RefGroupMemberRoleEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public RefGroupMemberRoleEntity findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RefGroupMemberRoleEntity", id));
    }

    public RefGroupMemberRoleEntity create(RefGroupMemberRoleEntity entity) {
        JpaReferenceResolver.resolveManyToOneReferences(entity, entityManager);
        return repository.save(entity);
    }

    public RefGroupMemberRoleEntity update(String id, RefGroupMemberRoleEntity incoming) {
        JpaReferenceResolver.resolveManyToOneReferences(incoming, entityManager);
        RefGroupMemberRoleEntity existing = findById(id);
        BeanUtils.copyProperties(incoming, existing, JpaReferenceResolver.updateIgnoredProperties(RefGroupMemberRoleEntity.class));
        return repository.save(existing);
    }

    public void delete(String id) {
        RefGroupMemberRoleEntity existing = findById(id);
        repository.delete(existing);
    }
}
