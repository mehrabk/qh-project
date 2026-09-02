package ir.bank.qh.productbuilder.deposit.service;

import ir.bank.qh.productbuilder.deposit.entity.DepositProductClosureSettlementRule;
import ir.bank.qh.productbuilder.deposit.repository.DepositProductClosureSettlementRuleRepository;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for DepositProductClosureSettlementRule. Business-rule checks equivalent to the
 * original model's CHECK constraints can be added by overriding
 * beforeCreate()/beforeUpdate().
 */
@Service
@RequiredArgsConstructor
@Transactional("productBuilderTransactionManager")
public class DepositProductClosureSettlementRuleService extends AbstractCrudService<DepositProductClosureSettlementRule> {

    private final DepositProductClosureSettlementRuleRepository repository;

    @Override
    protected JpaRepository<DepositProductClosureSettlementRule, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "DepositProductClosureSettlementRule";
    }
}
