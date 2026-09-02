package ir.bank.qh.productbuilder.deposit.service;

import ir.bank.qh.productbuilder.deposit.entity.DepositProductAllowedTerm;
import ir.bank.qh.productbuilder.deposit.repository.DepositProductAllowedTermRepository;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for DepositProductAllowedTerm. Business-rule checks equivalent to the
 * original model's CHECK constraints can be added by overriding
 * beforeCreate()/beforeUpdate().
 */
@Service
@RequiredArgsConstructor
@Transactional("productBuilderTransactionManager")
public class DepositProductAllowedTermService extends AbstractCrudService<DepositProductAllowedTerm> {

    private final DepositProductAllowedTermRepository repository;

    @Override
    protected JpaRepository<DepositProductAllowedTerm, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "DepositProductAllowedTerm";
    }
}
