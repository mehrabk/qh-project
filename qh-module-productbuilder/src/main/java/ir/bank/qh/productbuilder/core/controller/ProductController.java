package ir.bank.qh.productbuilder.core.controller;

import ir.bank.qh.productbuilder.core.entity.Product;
import ir.bank.qh.productbuilder.core.service.ProductService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for Product.
 * Base path: /api/v1/product/core/products
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/core/products")
public class ProductController extends AbstractCrudController<Product, ProductService> {

    private final ProductService service;

    @Override
    protected ProductService service() {
        return service;
    }
}
