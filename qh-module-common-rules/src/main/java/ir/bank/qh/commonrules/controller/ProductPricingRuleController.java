package ir.bank.qh.commonrules.controller;

import ir.bank.qh.commonrules.entity.ProductPricingRule;
import ir.bank.qh.commonrules.service.ProductPricingRuleService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for ProductPricingRule.
 * Base path: /api/v1/product/commonrules/product-pricing-rules
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/commonrules/product-pricing-rules")
public class ProductPricingRuleController extends AbstractCrudController<ProductPricingRule, ProductPricingRuleService> {

    private final ProductPricingRuleService service;

    @Override
    protected ProductPricingRuleService service() {
        return service;
    }
}
