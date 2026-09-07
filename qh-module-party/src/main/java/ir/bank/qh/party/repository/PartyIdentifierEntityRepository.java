package ir.bank.qh.party.repository;

import ir.bank.qh.party.entity.PartyIdentifierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyIdentifierEntityRepository extends JpaRepository<PartyIdentifierEntity, Long> {
}
