package ir.bank.qh.productbuilder.loan.repository;

import ir.bank.qh.productbuilder.loan.entity.LoanProductProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for LoanProductProfile (loan_product_profile).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface LoanProductProfileRepository extends JpaRepository<LoanProductProfile, Long> {
}
