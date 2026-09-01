package ir.bank.qh.party.repository;

import ir.bank.qh.party.entity.PartyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyEntityRepository extends JpaRepository<PartyEntity, Long> {
}
