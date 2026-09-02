package ir.bank.qh.reference.service;

import ir.bank.qh.reference.entity.EconomicSubSection;
import ir.bank.qh.reference.repository.EconomicSubSectionRepository;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for EconomicSubSection. Business-rule checks equivalent to the
 * original model's CHECK constraints can be added by overriding
 * beforeCreate()/beforeUpdate().
 */
@Service
@RequiredArgsConstructor
@Transactional("productBuilderTransactionManager")
public class EconomicSubSectionService extends AbstractCrudService<EconomicSubSection> {

    private final EconomicSubSectionRepository repository;

    @Override
    protected JpaRepository<EconomicSubSection, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "EconomicSubSection";
    }
}
