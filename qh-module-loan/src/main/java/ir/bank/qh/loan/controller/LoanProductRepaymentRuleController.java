package ir.bank.qh.loan.controller;

import ir.bank.qh.loan.entity.LoanProductRepaymentRule;
import ir.bank.qh.loan.service.LoanProductRepaymentRuleService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for LoanProductRepaymentRule.
 * Base path: /api/v1/product/loan/loan-product-repayment-rules
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/loan/loan-product-repayment-rules")
public class LoanProductRepaymentRuleController extends AbstractCrudController<LoanProductRepaymentRule, LoanProductRepaymentRuleService> {

    private final LoanProductRepaymentRuleService service;

    @Override
    protected LoanProductRepaymentRuleService service() {
        return service;
    }
}
