package ir.bank.qh.party.core.repository;

import ir.bank.qh.party.core.entity.PartyMembershipEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyMembershipEntityRepository extends JpaRepository<PartyMembershipEntity, Long> {
}
