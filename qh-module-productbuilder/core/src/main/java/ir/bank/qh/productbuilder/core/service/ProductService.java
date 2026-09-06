package ir.bank.qh.productbuilder.core.service;

import ir.bank.qh.productbuilder.core.entity.Product;
import ir.bank.qh.productbuilder.core.enums.BalanceNature;
import ir.bank.qh.productbuilder.core.enums.ProductDomain;
import ir.bank.qh.productbuilder.core.repository.ProductRepository;
import ir.bank.qh.common.exception.BusinessRuleViolationException;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for Product.
 * <p>
 * Enforces the equivalent of the original model's CK_PRODUCT_CLASS_NATURE
 * check constraint: DEPOSIT products must carry a LIABILITY balance nature,
 * LOAN products must carry an ASSET balance nature.
 */
@Service
@RequiredArgsConstructor
@Transactional("productBuilderTransactionManager")
public class ProductService extends AbstractCrudService<Product> {

    private final ProductRepository repository;

    @Override
    protected JpaRepository<Product, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "Product";
    }

    @Override
    protected void beforeCreate(Product entity) {
        validate(entity);
    }

    @Override
    protected void beforeUpdate(Product existing, Product incoming) {
        validate(incoming);
    }

    private void validate(Product p) {
        if (p.getProductClassCode() == ProductDomain.DEPOSIT
                && p.getBalanceNatureCode() != BalanceNature.LIABILITY) {
            throw new BusinessRuleViolationException(
                    "محصول از نوع DEPOSIT باید BALANCE_NATURE_CODE=LIABILITY داشته باشد (CK_PRODUCT_CLASS_NATURE)");
        }
        if (p.getProductClassCode() == ProductDomain.LOAN
                && p.getBalanceNatureCode() != BalanceNature.ASSET) {
            throw new BusinessRuleViolationException(
                    "محصول از نوع LOAN باید BALANCE_NATURE_CODE=ASSET داشته باشد (CK_PRODUCT_CLASS_NATURE)");
        }
    }
}
