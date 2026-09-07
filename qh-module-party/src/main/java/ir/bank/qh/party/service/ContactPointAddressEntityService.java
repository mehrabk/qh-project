package ir.bank.qh.party.service;

import ir.bank.qh.party.entity.ContactPointAddressEntity;
import ir.bank.qh.party.repository.ContactPointAddressEntityRepository;
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
 * Dedicated service for ContactPointAddressEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), matching every other Party service.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class ContactPointAddressEntityService {

    private final ContactPointAddressEntityRepository repository;

    @PersistenceContext(unitName = "party")
    private EntityManager entityManager;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<ContactPointAddressEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public ContactPointAddressEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ContactPointAddressEntity", id));
    }

    public ContactPointAddressEntity create(ContactPointAddressEntity entity) {
        JpaReferenceResolver.resolveManyToOneReferences(entity, entityManager);
        return repository.save(entity);
    }

    public ContactPointAddressEntity update(Long id, ContactPointAddressEntity incoming) {
        JpaReferenceResolver.resolveManyToOneReferences(incoming, entityManager);
        ContactPointAddressEntity existing = findById(id);
        BeanUtils.copyProperties(incoming, existing, JpaReferenceResolver.updateIgnoredProperties(ContactPointAddressEntity.class));
        return repository.save(existing);
    }

    public void delete(Long id) {
        ContactPointAddressEntity existing = findById(id);
        repository.delete(existing);
    }
}
