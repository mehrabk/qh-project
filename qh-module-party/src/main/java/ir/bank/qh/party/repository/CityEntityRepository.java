package ir.bank.qh.party.repository;

import ir.bank.qh.party.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityEntityRepository extends JpaRepository<CityEntity, Long> {
}
