package ir.bank.qh.party.repository;

import ir.bank.qh.party.entity.PartyRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyRoleEntityRepository extends JpaRepository<PartyRoleEntity, Long> {
}
