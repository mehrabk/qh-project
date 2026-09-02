package ir.bank.qh.productbuilder.deposit.controller;

import ir.bank.qh.productbuilder.deposit.entity.DepositProductTransactionRule;
import ir.bank.qh.productbuilder.deposit.service.DepositProductTransactionRuleService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for DepositProductTransactionRule.
 * Base path: /api/v1/product/deposit/deposit-product-transaction-rules
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/deposit/deposit-product-transaction-rules")
public class DepositProductTransactionRuleController extends AbstractCrudController<DepositProductTransactionRule, DepositProductTransactionRuleService> {

    private final DepositProductTransactionRuleService service;

    @Override
    protected DepositProductTransactionRuleService service() {
        return service;
    }
}
