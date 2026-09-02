package ir.bank.qh.productbuilder.deposit.controller;

import ir.bank.qh.productbuilder.deposit.entity.DepositProductJointRule;
import ir.bank.qh.productbuilder.deposit.service.DepositProductJointRuleService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for DepositProductJointRule.
 * Base path: /api/v1/product/deposit/deposit-product-joint-rules
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/deposit/deposit-product-joint-rules")
public class DepositProductJointRuleController extends AbstractCrudController<DepositProductJointRule, DepositProductJointRuleService> {

    private final DepositProductJointRuleService service;

    @Override
    protected DepositProductJointRuleService service() {
        return service;
    }
}
