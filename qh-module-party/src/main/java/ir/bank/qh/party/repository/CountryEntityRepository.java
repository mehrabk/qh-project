package ir.bank.qh.party.repository;

import ir.bank.qh.party.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryEntityRepository extends JpaRepository<CountryEntity, Long> {
}
