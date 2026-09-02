package ir.bank.qh.productbuilder.deposit.service;

import ir.bank.qh.productbuilder.deposit.entity.DepositProductProfile;
import ir.bank.qh.productbuilder.deposit.repository.DepositProductProfileRepository;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for DepositProductProfile. Business-rule checks equivalent to the
 * original model's CHECK constraints can be added by overriding
 * beforeCreate()/beforeUpdate().
 */
@Service
@RequiredArgsConstructor
@Transactional("productBuilderTransactionManager")
public class DepositProductProfileService extends AbstractCrudService<DepositProductProfile> {

    private final DepositProductProfileRepository repository;

    @Override
    protected JpaRepository<DepositProductProfile, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "DepositProductProfile";
    }
}
