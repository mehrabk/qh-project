package ir.bank.qh.party.service;

import ir.bank.qh.party.entity.PartyGroupEntity;
import ir.bank.qh.party.repository.PartyGroupEntityRepository;
import ir.bank.qh.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Dedicated service for PartyGroupEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), so this module stays fully
 * self-contained and free to diverge with entity-specific business rules.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class PartyGroupEntityService {

    private final PartyGroupEntityRepository repository;

    @Transactional(readOnly = true)
    public List<PartyGroupEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public PartyGroupEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PartyGroupEntity", id));
    }

    public PartyGroupEntity create(PartyGroupEntity entity) {
        return repository.save(entity);
    }

    public PartyGroupEntity update(Long id, PartyGroupEntity incoming) {
        // Ensures the row actually exists (throws 404 otherwise) before saving,
        // and forces the update to target that exact row rather than trusting
        // whatever id happened to be in the request body.
        findById(id);
        incoming.setId(id);
        return repository.save(incoming);
    }

    public void delete(Long id) {
        PartyGroupEntity existing = findById(id);
        repository.delete(existing);
    }
}
