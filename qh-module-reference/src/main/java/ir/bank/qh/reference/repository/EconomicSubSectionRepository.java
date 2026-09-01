package ir.bank.qh.reference.repository;

import ir.bank.qh.reference.entity.EconomicSubSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for EconomicSubSection (economic_sub_section).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface EconomicSubSectionRepository extends JpaRepository<EconomicSubSection, Long> {
}
