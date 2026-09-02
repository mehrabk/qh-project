package ir.bank.qh.party.service;

import ir.bank.qh.party.entity.PartyInquiryEntity;
import ir.bank.qh.party.repository.PartyInquiryEntityRepository;
import ir.bank.qh.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Dedicated service for PartyInquiryEntity. Every operation is implemented directly
 * here (no shared AbstractCrudService base), so this module stays fully
 * self-contained and free to diverge with entity-specific business rules.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class PartyInquiryEntityService {

    private final PartyInquiryEntityRepository repository;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<PartyInquiryEntity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public PartyInquiryEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PartyInquiryEntity", id));
    }

    public PartyInquiryEntity create(PartyInquiryEntity entity) {
        return repository.save(entity);
    }

    public PartyInquiryEntity update(Long id, PartyInquiryEntity incoming) {
        // Ensures the row actually exists (throws 404 otherwise) before saving,
        // and forces the update to target that exact row rather than trusting
        // whatever id happened to be in the request body.
        findById(id);
        incoming.setId(id);
        return repository.save(incoming);
    }

    public void delete(Long id) {
        PartyInquiryEntity existing = findById(id);
        repository.delete(existing);
    }
}
