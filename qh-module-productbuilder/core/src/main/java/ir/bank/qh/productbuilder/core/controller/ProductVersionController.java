package ir.bank.qh.productbuilder.core.controller;

import ir.bank.qh.productbuilder.core.entity.ProductVersion;
import ir.bank.qh.productbuilder.core.service.ProductVersionService;
import ir.bank.qh.common.controller.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * REST endpoints for ProductVersion, plus the lifecycle transitions described
 * in section 13 of the product-builder guide:
 * DRAFT -> (approve) -> APPROVED -> (enable-origination) -> ENABLED/ENABLED.
 * Base path: /api/v1/product/core/product-versions
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/core/product-versions")
public class ProductVersionController {

    private final ProductVersionService service;

    @GetMapping
    public ApiResponse<List<ProductVersion>> findAll() {
        return ApiResponse.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductVersion> findById(@PathVariable Long id) {
        return ApiResponse.ok(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ProductVersion> create(@Valid @RequestBody ProductVersion entity) {
        return ApiResponse.ok(service.create(entity), "با موفقیت ایجاد شد");
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductVersion> update(@PathVariable Long id, @Valid @RequestBody ProductVersion entity) {
        return ApiResponse.ok(service.update(id, entity), "با موفقیت به‌روزرسانی شد");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.ok(null, "با موفقیت حذف شد");
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
