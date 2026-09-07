package ir.bank.qh.party.service;

import ir.bank.qh.party.entity.ContactPointEntity;
import ir.bank.qh.party.repository.ContactPointEntityRepository;
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
 * Dedicated service for ContactPointEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), matching every other Party service.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class ContactPointEntityService {

    private final ContactPointEntityRepository repository;

    @PersistenceContext(unitName = "party")
    private EntityManager entityManager;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<ContactPointEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public ContactPointEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ContactPointEntity", id));
    }

    public ContactPointEntity create(ContactPointEntity entity) {
        JpaReferenceResolver.resolveManyToOneReferences(entity, entityManager);
        return repository.save(entity);
    }

    public ContactPointEntity update(Long id, ContactPointEntity incoming) {
        JpaReferenceResolver.resolveManyToOneReferences(incoming, entityManager);
        ContactPointEntity existing = findById(id);
        BeanUtils.copyProperties(incoming, existing, JpaReferenceResolver.updateIgnoredProperties(ContactPointEntity.class));
        return repository.save(existing);
    }

    public void delete(Long id) {
        ContactPointEntity existing = findById(id);
        repository.delete(existing);
    }
}
