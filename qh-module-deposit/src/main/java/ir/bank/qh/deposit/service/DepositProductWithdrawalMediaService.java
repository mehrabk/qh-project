package ir.bank.qh.deposit.service;

import ir.bank.qh.deposit.entity.DepositProductWithdrawalMedia;
import ir.bank.qh.deposit.repository.DepositProductWithdrawalMediaRepository;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for DepositProductWithdrawalMedia. Business-rule checks equivalent to the
 * original model's CHECK constraints can be added by overriding
 * beforeCreate()/beforeUpdate().
 */
@Service
@RequiredArgsConstructor
@Transactional("productBuilderTransactionManager")
public class DepositProductWithdrawalMediaService extends AbstractCrudService<DepositProductWithdrawalMedia> {

    private final DepositProductWithdrawalMediaRepository repository;

    @Override
    protected JpaRepository<DepositProductWithdrawalMedia, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "DepositProductWithdrawalMedia";
    }
}
