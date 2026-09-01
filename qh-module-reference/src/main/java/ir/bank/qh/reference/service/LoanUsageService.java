package ir.bank.qh.reference.service;

import ir.bank.qh.reference.entity.LoanUsage;
import ir.bank.qh.reference.repository.LoanUsageRepository;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for LoanUsage. Business-rule checks equivalent to the
 * original model's CHECK constraints can be added by overriding
 * beforeCreate()/beforeUpdate().
 */
@Service
@RequiredArgsConstructor
@Transactional
public class LoanUsageService extends AbstractCrudService<LoanUsage> {

    private final LoanUsageRepository repository;

    @Override
    protected JpaRepository<LoanUsage, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "LoanUsage";
    }
}
