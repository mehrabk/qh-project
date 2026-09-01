package ir.bank.qh.deposit.controller;

import ir.bank.qh.deposit.entity.DepositProductClosureRule;
import ir.bank.qh.deposit.service.DepositProductClosureRuleService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for DepositProductClosureRule.
 * Base path: /api/v1/product/deposit/deposit-product-closure-rules
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/deposit/deposit-product-closure-rules")
public class DepositProductClosureRuleController extends AbstractCrudController<DepositProductClosureRule, DepositProductClosureRuleService> {

    private final DepositProductClosureRuleService service;

    @Override
    protected DepositProductClosureRuleService service() {
        return service;
    }
}
