package ir.bank.qh.reference.repository;

import ir.bank.qh.reference.entity.LoanProductCollateral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for LoanProductCollateral (loan_product_collateral).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface LoanProductCollateralRepository extends JpaRepository<LoanProductCollateral, Long> {
}
