package ir.bank.qh.party.repository;

import ir.bank.qh.party.entity.ContactPointAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactPointAddressEntityRepository extends JpaRepository<ContactPointAddressEntity, Long> {
}
