package ir.bank.qh.party.repository;

import ir.bank.qh.party.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonEntityRepository extends JpaRepository<PersonEntity, Long> {
}
