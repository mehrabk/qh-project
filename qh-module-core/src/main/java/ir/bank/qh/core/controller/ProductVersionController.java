package ir.bank.qh.core.controller;

import ir.bank.qh.core.entity.ProductVersion;
import ir.bank.qh.core.service.ProductVersionService;
import ir.bank.qh.common.controller.AbstractCrudController;
import ir.bank.qh.common.controller.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for ProductVersion, plus the lifecycle transitions described
 * in section 13 of the product-builder guide:
 * DRAFT -> (approve) -> APPROVED -> (enable-origination) -> ENABLED/ENABLED.
 * Base path: /api/v1/product/core/product-versions
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/core/product-versions")
public class ProductVersionController extends AbstractCrudController<ProductVersion, ProductVersionService> {

    private final ProductVersionService service;

    @Override
    protected ProductVersionService service() {
        return service;
    }

    /** DRAFT -> APPROVED */
    @PostMapping("/{id}/approve")
    public ApiResponse<ProductVersion> approve(@PathVariable Long id,
                                                @RequestParam(defaultValue = "PRODUCT_COMMITTEE") String approvedBy) {
        return ApiResponse.ok(service.approve(id, approvedBy), "نسخه تصویب شد");
    }

    /** APPROVED -> ORIGINATION_STATUS_CODE = ENABLED, SERVICING_STATUS_CODE = ENABLED */
    @PostMapping("/{id}/enable-origination")
    public ApiResponse<ProductVersion> enableOrigination(@PathVariable Long id) {
        return ApiResponse.ok(service.enableOrigination(id), "افتتاح/اعطا برای این نسخه فعال شد");
    }

    /** Stops new origination while keeping servicing for existing accounts/contracts. */
    @PostMapping("/{id}/disable-origination")
    public ApiResponse<ProductVersion> disableOrigination(@PathVariable Long id) {
        return ApiResponse.ok(service.disableOrigination(id), "افتتاح/اعطای جدید برای این نسخه غیرفعال شد");
    }
}
