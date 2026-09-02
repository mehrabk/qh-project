package ir.bank.qh.productbuilder.core.controller;

import ir.bank.qh.productbuilder.core.entity.ProductRelationship;
import ir.bank.qh.productbuilder.core.service.ProductRelationshipService;
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
