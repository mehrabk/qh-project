package ir.bank.qh.productbuilder.deposit.controller;

import ir.bank.qh.productbuilder.deposit.entity.DepositProductClosureApprovalRule;
import ir.bank.qh.productbuilder.deposit.service.DepositProductClosureApprovalRuleService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for DepositProductClosureApprovalRule.
 * Base path: /api/v1/product/deposit/deposit-product-closure-approval-rules
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/deposit/deposit-product-closure-approval-rules")
public class DepositProductClosureApprovalRuleController extends AbstractCrudController<DepositProductClosureApprovalRule, DepositProductClosureApprovalRuleService> {

    private final DepositProductClosureApprovalRuleService service;

    @Override
    protected DepositProductClosureApprovalRuleService service() {
        return service;
    }
}
