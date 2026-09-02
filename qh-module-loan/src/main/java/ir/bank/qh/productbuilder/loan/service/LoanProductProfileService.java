package ir.bank.qh.productbuilder.loan.service;

import ir.bank.qh.productbuilder.loan.entity.LoanProductProfile;
import ir.bank.qh.productbuilder.loan.repository.LoanProductProfileRepository;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for LoanProductProfile. Business-rule checks equivalent to the
 * original model's CHECK constraints can be added by overriding
 * beforeCreate()/beforeUpdate().
 */
@Service
@RequiredArgsConstructor
@Transactional("productBuilderTransactionManager")
public class LoanProductProfileService extends AbstractCrudService<LoanProductProfile> {

    private final LoanProductProfileRepository repository;

    @Override
    protected JpaRepository<LoanProductProfile, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "LoanProductProfile";
    }
}
