package ir.bank.qh.party.repository;

import ir.bank.qh.party.entity.OrganizationOfficerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationOfficerEntityRepository extends JpaRepository<OrganizationOfficerEntity, Long> {
}
