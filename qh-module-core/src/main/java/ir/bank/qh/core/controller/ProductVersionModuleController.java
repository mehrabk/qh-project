package ir.bank.qh.core.controller;

import ir.bank.qh.core.entity.ProductVersionModule;
import ir.bank.qh.core.service.ProductVersionModuleService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for ProductVersionModule.
 * Base path: /api/v1/product/core/product-version-modules
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/core/product-version-modules")
public class ProductVersionModuleController extends AbstractCrudController<ProductVersionModule, ProductVersionModuleService> {

    private final ProductVersionModuleService service;

    @Override
    protected ProductVersionModuleService service() {
        return service;
    }
}
