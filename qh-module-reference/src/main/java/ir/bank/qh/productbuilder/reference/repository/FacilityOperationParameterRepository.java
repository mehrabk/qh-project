package ir.bank.qh.productbuilder.reference.repository;

import ir.bank.qh.productbuilder.reference.entity.FacilityOperationParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for FacilityOperationParameter (facility_operation_parameter).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface FacilityOperationParameterRepository extends JpaRepository<FacilityOperationParameter, Long> {
}
