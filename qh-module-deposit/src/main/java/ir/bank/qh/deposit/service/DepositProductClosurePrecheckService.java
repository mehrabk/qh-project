package ir.bank.qh.deposit.service;

import ir.bank.qh.deposit.entity.DepositProductClosurePrecheck;
import ir.bank.qh.deposit.repository.DepositProductClosurePrecheckRepository;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for DepositProductClosurePrecheck. Business-rule checks equivalent to the
 * original model's CHECK constraints can be added by overriding
 * beforeCreate()/beforeUpdate().
 */
@Service
@RequiredArgsConstructor
@Transactional("productBuilderTransactionManager")
public class DepositProductClosurePrecheckService extends AbstractCrudService<DepositProductClosurePrecheck> {

    private final DepositProductClosurePrecheckRepository repository;

    @Override
    protected JpaRepository<DepositProductClosurePrecheck, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "DepositProductClosurePrecheck";
    }
}
