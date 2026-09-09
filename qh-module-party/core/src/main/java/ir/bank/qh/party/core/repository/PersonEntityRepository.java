package ir.bank.qh.party.core.repository;

import ir.bank.qh.party.core.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonEntityRepository extends JpaRepository<PersonEntity, Long> {
}
