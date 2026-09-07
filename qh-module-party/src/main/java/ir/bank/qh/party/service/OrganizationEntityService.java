package ir.bank.qh.party.service;

import ir.bank.qh.party.entity.OrganizationEntity;
import ir.bank.qh.party.repository.OrganizationEntityRepository;
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
 * Dedicated service for OrganizationEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), matching every other Party service.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class OrganizationEntityService {

    private final OrganizationEntityRepository repository;

    @PersistenceContext(unitName = "party")
    private EntityManager entityManager;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<OrganizationEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public OrganizationEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrganizationEntity", id));
    }

    public OrganizationEntity create(OrganizationEntity entity) {
        JpaReferenceResolver.resolveManyToOneReferences(entity, entityManager);
        return repository.save(entity);
    }

    public OrganizationEntity update(Long id, OrganizationEntity incoming) {
        JpaReferenceResolver.resolveManyToOneReferences(incoming, entityManager);
        OrganizationEntity existing = findById(id);
        BeanUtils.copyProperties(incoming, existing, JpaReferenceResolver.updateIgnoredProperties(OrganizationEntity.class));
        return repository.save(existing);
    }

    public void delete(Long id) {
        OrganizationEntity existing = findById(id);
        repository.delete(existing);
    }
}
