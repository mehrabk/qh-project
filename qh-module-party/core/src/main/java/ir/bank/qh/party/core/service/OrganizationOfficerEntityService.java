package ir.bank.qh.party.core.service;

import ir.bank.qh.party.core.entity.OrganizationOfficerEntity;
import ir.bank.qh.party.core.repository.OrganizationOfficerEntityRepository;
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
 * Dedicated service for OrganizationOfficerEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), matching every other Party service.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class OrganizationOfficerEntityService {

    private final OrganizationOfficerEntityRepository repository;

    @PersistenceContext(unitName = "party")
    private EntityManager entityManager;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<OrganizationOfficerEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public OrganizationOfficerEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrganizationOfficerEntity", id));
    }

    public OrganizationOfficerEntity create(OrganizationOfficerEntity entity) {
        JpaReferenceResolver.resolveManyToOneReferences(entity, entityManager);
        return repository.save(entity);
    }

    public OrganizationOfficerEntity update(Long id, OrganizationOfficerEntity incoming) {
        JpaReferenceResolver.resolveManyToOneReferences(incoming, entityManager);
        OrganizationOfficerEntity existing = findById(id);
        BeanUtils.copyProperties(incoming, existing, JpaReferenceResolver.updateIgnoredProperties(OrganizationOfficerEntity.class));
        return repository.save(existing);
    }

    public void delete(Long id) {
        OrganizationOfficerEntity existing = findById(id);
        repository.delete(existing);
    }
}
