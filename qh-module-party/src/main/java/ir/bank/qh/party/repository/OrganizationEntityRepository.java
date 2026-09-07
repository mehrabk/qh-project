package ir.bank.qh.party.repository;

import ir.bank.qh.party.entity.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationEntityRepository extends JpaRepository<OrganizationEntity, Long> {
}
