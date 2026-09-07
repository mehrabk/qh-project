package ir.bank.qh.party.repository;

import ir.bank.qh.party.entity.PartyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyEntityRepository extends JpaRepository<PartyEntity, Long> {
}
