package ir.bank.qh.commonrules.service;

import ir.bank.qh.commonrules.entity.ProductEligibilityRule;
import ir.bank.qh.commonrules.repository.ProductEligibilityRuleRepository;
import ir.bank.qh.common.exception.BusinessRuleViolationException;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for ProductEligibilityRule.
 * <p>
 * Enforces the equivalent of the original model's CK_PER_AGE_RANGE and
 * CK_PER_AGE check constraints.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ProductEligibilityRuleService extends AbstractCrudService<ProductEligibilityRule> {

    private final ProductEligibilityRuleRepository repository;

    @Override
    protected JpaRepository<ProductEligibilityRule, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "ProductEligibilityRule";
    }

    @Override
    protected void beforeCreate(ProductEligibilityRule entity) {
        validate(entity);
    }

    @Override
    protected void beforeUpdate(ProductEligibilityRule existing, ProductEligibilityRule incoming) {
        validate(incoming);
    }

    private void validate(ProductEligibilityRule r) {
        if (r.getMinAge() != null && r.getMinAge() < 0) {
            throw new BusinessRuleViolationException("MIN_AGE نمی‌تواند منفی باشد (CK_PER_AGE)");
        }
        if (r.getMinAge() != null && r.getMaxAge() != null && r.getMinAge() > r.getMaxAge()) {
            throw new BusinessRuleViolationException(
                    "MIN_AGE نمی‌تواند بزرگ‌تر از MAX_AGE باشد (CK_PER_AGE_RANGE)");
        }
    }
}
