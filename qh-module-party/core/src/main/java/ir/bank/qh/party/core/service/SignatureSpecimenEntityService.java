package ir.bank.qh.party.core.service;

import ir.bank.qh.party.core.entity.SignatureSpecimenEntity;
import ir.bank.qh.party.core.repository.SignatureSpecimenEntityRepository;
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
 * Dedicated service for SignatureSpecimenEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), matching every other Party service.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class SignatureSpecimenEntityService {

    private final SignatureSpecimenEntityRepository repository;

    @PersistenceContext(unitName = "party")
    private EntityManager entityManager;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<SignatureSpecimenEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public SignatureSpecimenEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SignatureSpecimenEntity", id));
    }

    public SignatureSpecimenEntity create(SignatureSpecimenEntity entity) {
        JpaReferenceResolver.resolveManyToOneReferences(entity, entityManager);
        return repository.save(entity);
    }

    public SignatureSpecimenEntity update(Long id, SignatureSpecimenEntity incoming) {
        JpaReferenceResolver.resolveManyToOneReferences(incoming, entityManager);
        SignatureSpecimenEntity existing = findById(id);
        BeanUtils.copyProperties(incoming, existing, JpaReferenceResolver.updateIgnoredProperties(SignatureSpecimenEntity.class));
        return repository.save(existing);
    }

    public void delete(Long id) {
        SignatureSpecimenEntity existing = findById(id);
        repository.delete(existing);
    }
}
