package ir.bank.qh.deposit.controller;

import ir.bank.qh.deposit.entity.DepositProductOpeningRule;
import ir.bank.qh.deposit.service.DepositProductOpeningRuleService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for DepositProductOpeningRule.
 * Base path: /api/v1/product/deposit/deposit-product-opening-rules
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/deposit/deposit-product-opening-rules")
public class DepositProductOpeningRuleController extends AbstractCrudController<DepositProductOpeningRule, DepositProductOpeningRuleService> {

    private final DepositProductOpeningRuleService service;

    @Override
    protected DepositProductOpeningRuleService service() {
        return service;
    }
}
