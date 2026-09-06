package ir.bank.qh.productbuilder.core.controller;

import ir.bank.qh.productbuilder.core.entity.ProductRelationship;
import ir.bank.qh.productbuilder.core.service.ProductRelationshipService;
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

/**
 * REST endpoints for ProductRelationship.
 * Base path: /api/v1/product/core/product-relationships
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/core/product-relationships")
public class ProductRelationshipController {

    private final ProductRelationshipService service;

    @GetMapping
    public ApiResponse<List<ProductRelationship>> findAll() {
        return ApiResponse.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductRelationship> findById(@PathVariable Long id) {
        return ApiResponse.ok(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ProductRelationship> create(@Valid @RequestBody ProductRelationship entity) {
        return ApiResponse.ok(service.create(entity), "با موفقیت ایجاد شد");
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductRelationship> update(@PathVariable Long id, @Valid @RequestBody ProductRelationship entity) {
        return ApiResponse.ok(service.update(id, entity), "با موفقیت به‌روزرسانی شد");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.ok(null, "با موفقیت حذف شد");
    }
}
