package ir.bank.qh.productbuilder.commonrules.controller;

import ir.bank.qh.productbuilder.commonrules.entity.ProductEligibilityRule;
import ir.bank.qh.productbuilder.commonrules.service.ProductEligibilityRuleService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for ProductEligibilityRule.
 * Base path: /api/v1/product/commonrules/product-eligibility-rules
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/commonrules/product-eligibility-rules")
public class ProductEligibilityRuleController extends AbstractCrudController<ProductEligibilityRule, ProductEligibilityRuleService> {

    private final ProductEligibilityRuleService service;

    @Override
    protected ProductEligibilityRuleService service() {
        return service;
    }
}
