package ir.bank.qh.productbuilder.commonrules.controller;

import ir.bank.qh.productbuilder.commonrules.entity.ProductRateTier;
import ir.bank.qh.productbuilder.commonrules.service.ProductRateTierService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for ProductRateTier.
 * Base path: /api/v1/product/commonrules/product-rate-tiers
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/commonrules/product-rate-tiers")
public class ProductRateTierController extends AbstractCrudController<ProductRateTier, ProductRateTierService> {

    private final ProductRateTierService service;

    @Override
    protected ProductRateTierService service() {
        return service;
    }
}
