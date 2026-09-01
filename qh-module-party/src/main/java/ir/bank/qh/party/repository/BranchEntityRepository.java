package ir.bank.qh.party.repository;

import ir.bank.qh.party.entity.BranchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchEntityRepository extends JpaRepository<BranchEntity, Long> {
}
