package ir.bank.qh.productbuilder.commonrules.controller;

import ir.bank.qh.productbuilder.commonrules.entity.ProductChannelRule;
import ir.bank.qh.productbuilder.commonrules.service.ProductChannelRuleService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for ProductChannelRule.
 * Base path: /api/v1/product/commonrules/product-channel-rules
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/commonrules/product-channel-rules")
public class ProductChannelRuleController extends AbstractCrudController<ProductChannelRule, ProductChannelRuleService> {

    private final ProductChannelRuleService service;

    @Override
    protected ProductChannelRuleService service() {
        return service;
    }
}
