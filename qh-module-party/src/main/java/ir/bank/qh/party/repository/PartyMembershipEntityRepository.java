package ir.bank.qh.party.repository;

import ir.bank.qh.party.entity.PartyMembershipEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyMembershipEntityRepository extends JpaRepository<PartyMembershipEntity, Long> {
}
