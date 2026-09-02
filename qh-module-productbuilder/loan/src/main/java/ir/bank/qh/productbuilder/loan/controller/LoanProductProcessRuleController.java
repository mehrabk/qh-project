package ir.bank.qh.productbuilder.loan.controller;

import ir.bank.qh.productbuilder.loan.entity.LoanProductProcessRule;
import ir.bank.qh.productbuilder.loan.service.LoanProductProcessRuleService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for LoanProductProcessRule.
 * Base path: /api/v1/product/loan/loan-product-process-rules
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/loan/loan-product-process-rules")
public class LoanProductProcessRuleController extends AbstractCrudController<LoanProductProcessRule, LoanProductProcessRuleService> {

    private final LoanProductProcessRuleService service;

    @Override
    protected LoanProductProcessRuleService service() {
        return service;
    }
}
