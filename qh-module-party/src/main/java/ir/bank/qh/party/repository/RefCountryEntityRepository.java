package ir.bank.qh.party.repository;

import ir.bank.qh.party.entity.RefCountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefCountryEntityRepository extends JpaRepository<RefCountryEntity, String> {
}
