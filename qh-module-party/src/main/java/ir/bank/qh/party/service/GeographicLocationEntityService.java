package ir.bank.qh.party.service;

import ir.bank.qh.party.entity.GeographicLocationEntity;
import ir.bank.qh.party.repository.GeographicLocationEntityRepository;
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
 * Dedicated service for GeographicLocationEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), matching every other Party service.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class GeographicLocationEntityService {

    private final GeographicLocationEntityRepository repository;

    @PersistenceContext(unitName = "party")
    private EntityManager entityManager;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<GeographicLocationEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public GeographicLocationEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GeographicLocationEntity", id));
    }

    public GeographicLocationEntity create(GeographicLocationEntity entity) {
        JpaReferenceResolver.resolveManyToOneReferences(entity, entityManager);
        return repository.save(entity);
    }

    public GeographicLocationEntity update(Long id, GeographicLocationEntity incoming) {
        JpaReferenceResolver.resolveManyToOneReferences(incoming, entityManager);
        GeographicLocationEntity existing = findById(id);
        BeanUtils.copyProperties(incoming, existing, JpaReferenceResolver.updateIgnoredProperties(GeographicLocationEntity.class));
        return repository.save(existing);
    }

    public void delete(Long id) {
        GeographicLocationEntity existing = findById(id);
        repository.delete(existing);
    }
}
