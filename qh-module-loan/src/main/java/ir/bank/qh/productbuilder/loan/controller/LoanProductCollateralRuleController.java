package ir.bank.qh.productbuilder.loan.controller;

import ir.bank.qh.productbuilder.loan.entity.LoanProductCollateralRule;
import ir.bank.qh.productbuilder.loan.service.LoanProductCollateralRuleService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for LoanProductCollateralRule.
 * Base path: /api/v1/product/loan/loan-product-collateral-rules
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/loan/loan-product-collateral-rules")
public class LoanProductCollateralRuleController extends AbstractCrudController<LoanProductCollateralRule, LoanProductCollateralRuleService> {

    private final LoanProductCollateralRuleService service;

    @Override
    protected LoanProductCollateralRuleService service() {
        return service;
    }
}
