package ir.bank.qh.party.core.repository;

import ir.bank.qh.party.core.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerEntityRepository extends JpaRepository<CustomerEntity, Long> {
}
