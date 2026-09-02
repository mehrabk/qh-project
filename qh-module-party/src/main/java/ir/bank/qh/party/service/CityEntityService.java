package ir.bank.qh.party.service;

import ir.bank.qh.party.entity.CityEntity;
import ir.bank.qh.party.repository.CityEntityRepository;
import ir.bank.qh.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Dedicated service for CityEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), so this module stays fully
 * self-contained and free to diverge with entity-specific business rules.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class CityEntityService {

    private final CityEntityRepository repository;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<CityEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public CityEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CityEntity", id));
    }

    public CityEntity create(CityEntity entity) {
        return repository.save(entity);
    }

    public CityEntity update(Long id, CityEntity incoming) {
        // Ensures the row actually exists (throws 404 otherwise) before saving,
        // and forces the update to target that exact row rather than trusting
        // whatever id happened to be in the request body.
        findById(id);
        incoming.setId(id);
        return repository.save(incoming);
    }

    public void delete(Long id) {
        CityEntity existing = findById(id);
        repository.delete(existing);
    }
}
