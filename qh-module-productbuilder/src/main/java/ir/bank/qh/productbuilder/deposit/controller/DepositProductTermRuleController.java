package ir.bank.qh.productbuilder.deposit.controller;

import ir.bank.qh.productbuilder.deposit.entity.DepositProductTermRule;
import ir.bank.qh.productbuilder.deposit.service.DepositProductTermRuleService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for DepositProductTermRule.
 * Base path: /api/v1/product/deposit/deposit-product-term-rules
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/deposit/deposit-product-term-rules")
public class DepositProductTermRuleController extends AbstractCrudController<DepositProductTermRule, DepositProductTermRuleService> {

    private final DepositProductTermRuleService service;

    @Override
    protected DepositProductTermRuleService service() {
        return service;
    }
}
