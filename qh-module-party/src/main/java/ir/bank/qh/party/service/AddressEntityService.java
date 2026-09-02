package ir.bank.qh.party.service;

import ir.bank.qh.party.entity.AddressEntity;
import ir.bank.qh.party.repository.AddressEntityRepository;
import ir.bank.qh.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Dedicated service for AddressEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), so this module stays fully
 * self-contained and free to diverge with entity-specific business rules.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class AddressEntityService {

    private final AddressEntityRepository repository;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<AddressEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public AddressEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AddressEntity", id));
    }

    public AddressEntity create(AddressEntity entity) {
        return repository.save(entity);
    }

    public AddressEntity update(Long id, AddressEntity incoming) {
        // Ensures the row actually exists (throws 404 otherwise) before saving,
        // and forces the update to target that exact row rather than trusting
        // whatever id happened to be in the request body.
        findById(id);
        incoming.setId(id);
        return repository.save(incoming);
    }

    public void delete(Long id) {
        AddressEntity existing = findById(id);
        repository.delete(existing);
    }
}
