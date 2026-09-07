package ir.bank.qh.party.service;

import ir.bank.qh.party.entity.PartyEntity;
import ir.bank.qh.party.repository.PartyEntityRepository;
import ir.bank.qh.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Read-only service for PartyEntity: PARTY is the abstract JOINED-inheritance
 * root of PERSON/ORGANIZATION, so it is never created/updated/deleted directly
 * - do that through PersonService / OrganizationService instead. This service
 * only exposes polymorphic read access across the whole Party hierarchy.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, transactionManager = "partyTransactionManager")
public class PartyEntityService {

    private final PartyEntityRepository repository;

    public List<PartyEntity> findAll() {
        return repository.findAll();
    }

    public PartyEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PartyEntity", id));
    }
}
