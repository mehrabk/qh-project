package ir.bank.qh.productbuilder.reference.repository;

import ir.bank.qh.productbuilder.reference.entity.LoanTypePlanType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for LoanTypePlanType (loan_type_plan_type).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface LoanTypePlanTypeRepository extends JpaRepository<LoanTypePlanType, Long> {
}
