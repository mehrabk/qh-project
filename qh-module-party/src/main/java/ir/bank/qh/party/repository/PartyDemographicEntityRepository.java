package ir.bank.qh.party.repository;

import ir.bank.qh.party.entity.PartyDemographicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyDemographicEntityRepository extends JpaRepository<PartyDemographicEntity, Long> {
}
