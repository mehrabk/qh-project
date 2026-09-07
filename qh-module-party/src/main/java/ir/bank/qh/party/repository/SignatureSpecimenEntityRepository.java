package ir.bank.qh.party.repository;

import ir.bank.qh.party.entity.SignatureSpecimenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignatureSpecimenEntityRepository extends JpaRepository<SignatureSpecimenEntity, Long> {
}
