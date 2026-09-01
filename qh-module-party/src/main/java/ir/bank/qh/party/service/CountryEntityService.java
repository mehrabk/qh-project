package ir.bank.qh.party.service;

import ir.bank.qh.party.entity.CountryEntity;
import ir.bank.qh.party.repository.CountryEntityRepository;
import ir.bank.qh.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
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
@Transactional
public class CountryEntityService {

    private final CountryEntityRepository repository;

    @Transactional(readOnly = true)
    public List<CountryEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public CountryEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CountryEntity", id));
    }

    public CountryEntity create(CountryEntity entity) {
        return repository.save(entity);
    }

    public CountryEntity update(Long id, CountryEntity incoming) {
        // Ensures the row actually exists (throws 404 otherwise) before saving,
        // and forces the update to target that exact row rather than trusting
        // whatever id happened to be in the request body.
        findById(id);
        incoming.setId(id);
        return repository.save(incoming);
    }

    public void delete(Long id) {
        CountryEntity existing = findById(id);
        repository.delete(existing);
    }
}
