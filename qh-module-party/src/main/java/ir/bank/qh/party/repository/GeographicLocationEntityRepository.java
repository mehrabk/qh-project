package ir.bank.qh.party.repository;

import ir.bank.qh.party.entity.GeographicLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeographicLocationEntityRepository extends JpaRepository<GeographicLocationEntity, Long> {
}
