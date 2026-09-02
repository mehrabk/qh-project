package ir.bank.qh.productbuilder.commonrules.controller;

import ir.bank.qh.productbuilder.commonrules.entity.ProductPricingComponent;
import ir.bank.qh.productbuilder.commonrules.service.ProductPricingComponentService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for ProductPricingComponent.
 * Base path: /api/v1/product/commonrules/product-pricing-components
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/commonrules/product-pricing-components")
public class ProductPricingComponentController extends AbstractCrudController<ProductPricingComponent, ProductPricingComponentService> {

    private final ProductPricingComponentService service;

    @Override
    protected ProductPricingComponentService service() {
        return service;
    }
}
