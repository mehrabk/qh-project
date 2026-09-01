package ir.bank.qh.reference.repository;

import ir.bank.qh.reference.entity.EconomicSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for EconomicSection (economic_section).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface EconomicSectionRepository extends JpaRepository<EconomicSection, Long> {
}
