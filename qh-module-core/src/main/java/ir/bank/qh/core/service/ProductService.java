package ir.bank.qh.core.service;

import ir.bank.qh.core.entity.Product;
import ir.bank.qh.core.repository.ProductRepository;
import ir.bank.qh.common.exception.BusinessRuleViolationException;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Service for Product.
 * <p>
 * Enforces the equivalent of the original model's CK_PRODUCT_CLASS_NATURE
 * check constraint: DEPOSIT products must carry a LIABILITY balance nature,
 * LOAN products must carry an ASSET balance nature.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ProductService extends AbstractCrudService<Product> {

    private static final Set<String> VALID_CLASSES = Set.of("DEPOSIT", "LOAN");

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
        if (p.getProductClassCode() != null && !VALID_CLASSES.contains(p.getProductClassCode())) {
            throw new BusinessRuleViolationException(
                    "PRODUCT_CLASS_CODE باید یکی از DEPOSIT/LOAN باشد (CK_PRODUCT_CLASS)");
        }
        if ("DEPOSIT".equalsIgnoreCase(p.getProductClassCode())
                && !"LIABILITY".equalsIgnoreCase(p.getBalanceNatureCode())) {
            throw new BusinessRuleViolationException(
                    "محصول از نوع DEPOSIT باید BALANCE_NATURE_CODE=LIABILITY داشته باشد (CK_PRODUCT_CLASS_NATURE)");
        }
        if ("LOAN".equalsIgnoreCase(p.getProductClassCode())
                && !"ASSET".equalsIgnoreCase(p.getBalanceNatureCode())) {
            throw new BusinessRuleViolationException(
                    "محصول از نوع LOAN باید BALANCE_NATURE_CODE=ASSET داشته باشد (CK_PRODUCT_CLASS_NATURE)");
        }
    }
}
