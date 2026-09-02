package ir.bank.qh.core.service;

import ir.bank.qh.core.entity.ProductVersion;
import ir.bank.qh.core.repository.ProductVersionRepository;
import ir.bank.qh.common.exception.BusinessRuleViolationException;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for ProductVersion.
 * <p>
 * Enforces the equivalent of the original model's CK_PRODUCT_VERSION_DATES
 * and CK_PRODUCT_VERSION_NO check constraints, and the workflow rule that
 * origination cannot be enabled before the version has an APPROVED status.
 */
@Service
@RequiredArgsConstructor
@Transactional("productBuilderTransactionManager")
public class ProductVersionService extends AbstractCrudService<ProductVersion> {

    private final ProductVersionRepository repository;

    @Override
    protected JpaRepository<ProductVersion, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "ProductVersion";
    }

    @Override
    protected void beforeCreate(ProductVersion entity) {
        validate(entity);
    }

    @Override
    protected void beforeUpdate(ProductVersion existing, ProductVersion incoming) {
        validate(incoming);
    }

    /**
     * Moves a DRAFT version to APPROVED, recording who approved it and when.
     * Mirrors step "VALIDATION -> APPROVAL" of the product lifecycle.
     */
    public ProductVersion approve(Long id, String approvedBy) {
        ProductVersion v = findById(id);
        if (!"DRAFT".equalsIgnoreCase(v.getVersionStatusCode())) {
            throw new BusinessRuleViolationException("فقط نسخه‌های DRAFT قابل تصویب هستند");
        }
        v.setVersionStatusCode("APPROVED");
        v.setApprovedBy(approvedBy);
        v.setApprovedAt(java.time.LocalDateTime.now());
        return repository.save(v);
    }

    /**
     * Enables ORIGINATION_STATUS_CODE (allow new account/contract opening).
     * Only allowed once the version is APPROVED - mirrors step
     * "APPROVAL -> ORIGINATION_STATUS = ENABLED" of the product lifecycle.
     */
    public ProductVersion enableOrigination(Long id) {
        ProductVersion v = findById(id);
        if (!"APPROVED".equalsIgnoreCase(v.getVersionStatusCode())) {
            throw new BusinessRuleViolationException("Origination فقط برای نسخه‌های APPROVED فعال می‌شود");
        }
        v.setOriginationStatusCode("ENABLED");
        v.setServicingStatusCode("ENABLED");
        v.setIsCurrent(true);
        return repository.save(v);
    }

    /** Disables new origination while keeping servicing available for existing accounts/contracts. */
    public ProductVersion disableOrigination(Long id) {
        ProductVersion v = findById(id);
        v.setOriginationStatusCode("DISABLED");
        return repository.save(v);
    }

    private void validate(ProductVersion v) {
        if (v.getVersionNo() != null && v.getVersionNo() <= 0) {
            throw new BusinessRuleViolationException("VERSION_NO باید عددی مثبت باشد (CK_PRODUCT_VERSION_NO)");
        }
        if (v.getValidFrom() != null && v.getValidTo() != null && v.getValidFrom().isAfter(v.getValidTo())) {
            throw new BusinessRuleViolationException(
                    "VALID_FROM نمی‌تواند بعد از VALID_TO باشد (CK_PRODUCT_VERSION_DATES)");
        }
        if ("ENABLED".equalsIgnoreCase(v.getOriginationStatusCode())
                && !"APPROVED".equalsIgnoreCase(v.getVersionStatusCode())) {
            throw new BusinessRuleViolationException(
                    "ORIGINATION_STATUS_CODE فقط برای نسخه‌های APPROVED می‌تواند ENABLED شود");
        }
    }
}
