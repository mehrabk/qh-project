package ir.bank.qh.productbuilder.deposit.controller;

import ir.bank.qh.productbuilder.deposit.entity.DepositProductDormancyRule;
import ir.bank.qh.productbuilder.deposit.service.DepositProductDormancyRuleService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for DepositProductDormancyRule.
 * Base path: /api/v1/product/deposit/deposit-product-dormancy-rules
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/deposit/deposit-product-dormancy-rules")
public class DepositProductDormancyRuleController extends AbstractCrudController<DepositProductDormancyRule, DepositProductDormancyRuleService> {

    private final DepositProductDormancyRuleService service;

    @Override
    protected DepositProductDormancyRuleService service() {
        return service;
    }
}
