package ir.bank.qh.core.controller;

import ir.bank.qh.core.entity.ProductLegacyMapping;
import ir.bank.qh.core.service.ProductLegacyMappingService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for ProductLegacyMapping.
 * Base path: /api/v1/product/core/product-legacy-mappings
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/core/product-legacy-mappings")
public class ProductLegacyMappingController extends AbstractCrudController<ProductLegacyMapping, ProductLegacyMappingService> {

    private final ProductLegacyMappingService service;

    @Override
    protected ProductLegacyMappingService service() {
        return service;
    }
}
