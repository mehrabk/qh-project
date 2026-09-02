package ir.bank.qh.productbuilder.reference.service;

import ir.bank.qh.productbuilder.reference.entity.EconomicSection;
import ir.bank.qh.productbuilder.reference.repository.EconomicSectionRepository;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for EconomicSection. Business-rule checks equivalent to the
 * original model's CHECK constraints can be added by overriding
 * beforeCreate()/beforeUpdate().
 */
@Service
@RequiredArgsConstructor
@Transactional("productBuilderTransactionManager")
public class EconomicSectionService extends AbstractCrudService<EconomicSection> {

    private final EconomicSectionRepository repository;

    @Override
    protected JpaRepository<EconomicSection, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "EconomicSection";
    }
}
