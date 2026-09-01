package ir.bank.qh.party.repository;

import ir.bank.qh.party.entity.PartyAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyAddressEntityRepository extends JpaRepository<PartyAddressEntity, Long> {
}
