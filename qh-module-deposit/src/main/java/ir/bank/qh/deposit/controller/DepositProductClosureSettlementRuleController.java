package ir.bank.qh.deposit.controller;

import ir.bank.qh.deposit.entity.DepositProductClosureSettlementRule;
import ir.bank.qh.deposit.service.DepositProductClosureSettlementRuleService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for DepositProductClosureSettlementRule.
 * Base path: /api/v1/product/deposit/deposit-product-closure-settlement-rules
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/deposit/deposit-product-closure-settlement-rules")
public class DepositProductClosureSettlementRuleController extends AbstractCrudController<DepositProductClosureSettlementRule, DepositProductClosureSettlementRuleService> {

    private final DepositProductClosureSettlementRuleService service;

    @Override
    protected DepositProductClosureSettlementRuleService service() {
        return service;
    }
}
