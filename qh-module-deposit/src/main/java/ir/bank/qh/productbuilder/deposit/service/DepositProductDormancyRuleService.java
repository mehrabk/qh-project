package ir.bank.qh.productbuilder.deposit.service;

import ir.bank.qh.productbuilder.deposit.entity.DepositProductDormancyRule;
import ir.bank.qh.productbuilder.deposit.repository.DepositProductDormancyRuleRepository;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for DepositProductDormancyRule. Business-rule checks equivalent to the
 * original model's CHECK constraints can be added by overriding
 * beforeCreate()/beforeUpdate().
 */
@Service
@RequiredArgsConstructor
@Transactional("productBuilderTransactionManager")
public class DepositProductDormancyRuleService extends AbstractCrudService<DepositProductDormancyRule> {

    private final DepositProductDormancyRuleRepository repository;

    @Override
    protected JpaRepository<DepositProductDormancyRule, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "DepositProductDormancyRule";
    }
}
