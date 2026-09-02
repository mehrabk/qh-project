package ir.bank.qh.productbuilder.reference.service;

import ir.bank.qh.productbuilder.reference.entity.FacilityOperationParameter;
import ir.bank.qh.productbuilder.reference.repository.FacilityOperationParameterRepository;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for FacilityOperationParameter. Business-rule checks equivalent to the
 * original model's CHECK constraints can be added by overriding
 * beforeCreate()/beforeUpdate().
 */
@Service
@RequiredArgsConstructor
@Transactional("productBuilderTransactionManager")
public class FacilityOperationParameterService extends AbstractCrudService<FacilityOperationParameter> {

    private final FacilityOperationParameterRepository repository;

    @Override
    protected JpaRepository<FacilityOperationParameter, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "FacilityOperationParameter";
    }
}
