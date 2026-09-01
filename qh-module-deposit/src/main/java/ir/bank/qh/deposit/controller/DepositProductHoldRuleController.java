package ir.bank.qh.deposit.controller;

import ir.bank.qh.deposit.entity.DepositProductHoldRule;
import ir.bank.qh.deposit.service.DepositProductHoldRuleService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for DepositProductHoldRule.
 * Base path: /api/v1/product/deposit/deposit-product-hold-rules
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/deposit/deposit-product-hold-rules")
public class DepositProductHoldRuleController extends AbstractCrudController<DepositProductHoldRule, DepositProductHoldRuleService> {

    private final DepositProductHoldRuleService service;

    @Override
    protected DepositProductHoldRuleService service() {
        return service;
    }
}
