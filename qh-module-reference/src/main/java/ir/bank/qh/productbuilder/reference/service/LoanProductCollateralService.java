package ir.bank.qh.productbuilder.reference.service;

import ir.bank.qh.productbuilder.reference.entity.LoanProductCollateral;
import ir.bank.qh.productbuilder.reference.repository.LoanProductCollateralRepository;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for LoanProductCollateral. Business-rule checks equivalent to the
 * original model's CHECK constraints can be added by overriding
 * beforeCreate()/beforeUpdate().
 */
@Service
@RequiredArgsConstructor
@Transactional("productBuilderTransactionManager")
public class LoanProductCollateralService extends AbstractCrudService<LoanProductCollateral> {

    private final LoanProductCollateralRepository repository;

    @Override
    protected JpaRepository<LoanProductCollateral, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "LoanProductCollateral";
    }
}
