package ir.bank.qh.party.service;

import ir.bank.qh.party.entity.CountryEntity;
import ir.bank.qh.party.repository.CountryEntityRepository;
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
 * Dedicated service for CountryEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), so this module stays fully
 * self-contained and free to diverge with entity-specific business rules.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class CountryEntityService {

    private final CountryEntityRepository repository;

    @PersistenceContext(unitName = "party")
    private EntityManager entityManager;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<CountryEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public CountryEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CountryEntity", id));
    }

    public CountryEntity create(CountryEntity entity) {
        JpaReferenceResolver.resolveManyToOneReferences(entity, entityManager);
        return repository.save(entity);
    }

    public CountryEntity update(Long id, CountryEntity incoming) {
        JpaReferenceResolver.resolveManyToOneReferences(incoming, entityManager);
        CountryEntity existing = findById(id);
        BeanUtils.copyProperties(incoming, existing, JpaReferenceResolver.updateIgnoredProperties(CountryEntity.class));
        return repository.save(existing);
    }

    public void delete(Long id) {
        CountryEntity existing = findById(id);
        repository.delete(existing);
    }
}
