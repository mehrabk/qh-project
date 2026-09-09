package ir.bank.qh.party.reference.service;

import ir.bank.qh.party.reference.entity.RefContactAddressAssocTypeEntity;
import ir.bank.qh.party.reference.repository.RefContactAddressAssocTypeEntityRepository;
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
 * Dedicated service for RefContactAddressAssocTypeEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), matching every other Party service.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class RefContactAddressAssocTypeEntityService {

    private final RefContactAddressAssocTypeEntityRepository repository;

    @PersistenceContext(unitName = "party")
    private EntityManager entityManager;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<RefContactAddressAssocTypeEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public RefContactAddressAssocTypeEntity findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RefContactAddressAssocTypeEntity", id));
    }

    public RefContactAddressAssocTypeEntity create(RefContactAddressAssocTypeEntity entity) {
        JpaReferenceResolver.resolveManyToOneReferences(entity, entityManager);
        return repository.save(entity);
    }

    public RefContactAddressAssocTypeEntity update(String id, RefContactAddressAssocTypeEntity incoming) {
        JpaReferenceResolver.resolveManyToOneReferences(incoming, entityManager);
        RefContactAddressAssocTypeEntity existing = findById(id);
        BeanUtils.copyProperties(incoming, existing, JpaReferenceResolver.updateIgnoredProperties(RefContactAddressAssocTypeEntity.class));
        return repository.save(existing);
    }

    public void delete(String id) {
        RefContactAddressAssocTypeEntity existing = findById(id);
        repository.delete(existing);
    }
}
