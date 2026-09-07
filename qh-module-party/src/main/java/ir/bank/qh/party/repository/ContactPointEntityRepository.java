package ir.bank.qh.party.repository;

import ir.bank.qh.party.entity.ContactPointEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactPointEntityRepository extends JpaRepository<ContactPointEntity, Long> {
}
