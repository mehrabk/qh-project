package ir.bank.qh.party.service;

import ir.bank.qh.party.entity.BranchEntity;
import ir.bank.qh.party.repository.BranchEntityRepository;
import ir.bank.qh.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Dedicated service for BranchEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), so this module stays fully
 * self-contained and free to diverge with entity-specific business rules.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class BranchEntityService {

    private final BranchEntityRepository repository;

    @Transactional(readOnly = true)
    public List<BranchEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public BranchEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BranchEntity", id));
    }

    public BranchEntity create(BranchEntity entity) {
        return repository.save(entity);
    }

    public BranchEntity update(Long id, BranchEntity incoming) {
        // Ensures the row actually exists (throws 404 otherwise) before saving,
        // and forces the update to target that exact row rather than trusting
        // whatever id happened to be in the request body.
        findById(id);
        incoming.setId(id);
        return repository.save(incoming);
    }

    public void delete(Long id) {
        BranchEntity existing = findById(id);
        repository.delete(existing);
    }
}
