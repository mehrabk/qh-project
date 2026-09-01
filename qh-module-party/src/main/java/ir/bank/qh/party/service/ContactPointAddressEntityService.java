package ir.bank.qh.party.service;

import ir.bank.qh.party.entity.ContactPointAddressEntity;
import ir.bank.qh.party.repository.ContactPointAddressEntityRepository;
import ir.bank.qh.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Dedicated service for ContactPointAddressEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), so this module stays fully
 * self-contained and free to diverge with entity-specific business rules.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ContactPointAddressEntityService {

    private final ContactPointAddressEntityRepository repository;

    @Transactional(readOnly = true)
    public List<ContactPointAddressEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public ContactPointAddressEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ContactPointAddressEntity", id));
    }

    public ContactPointAddressEntity create(ContactPointAddressEntity entity) {
        return repository.save(entity);
    }

    public ContactPointAddressEntity update(Long id, ContactPointAddressEntity incoming) {
        // Ensures the row actually exists (throws 404 otherwise) before saving,
        // and forces the update to target that exact row rather than trusting
        // whatever id happened to be in the request body.
        findById(id);
        incoming.setId(id);
        return repository.save(incoming);
    }

    public void delete(Long id) {
        ContactPointAddressEntity existing = findById(id);
        repository.delete(existing);
    }
}
