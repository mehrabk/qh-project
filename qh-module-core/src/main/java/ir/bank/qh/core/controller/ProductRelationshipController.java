package ir.bank.qh.core.controller;

import ir.bank.qh.core.entity.ProductRelationship;
import ir.bank.qh.core.service.ProductRelationshipService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for ProductRelationship.
 * Base path: /api/v1/product/core/product-relationships
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/core/product-relationships")
public class ProductRelationshipController extends AbstractCrudController<ProductRelationship, ProductRelationshipService> {

    private final ProductRelationshipService service;

    @Override
    protected ProductRelationshipService service() {
        return service;
    }
}
