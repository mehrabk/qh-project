package ir.bank.qh.productbuilder.loan.controller;

import ir.bank.qh.productbuilder.loan.entity.LoanEligibilityExtension;
import ir.bank.qh.productbuilder.loan.service.LoanEligibilityExtensionService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for LoanEligibilityExtension.
 * Base path: /api/v1/product/loan/loan-eligibility-extensions
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/loan/loan-eligibility-extensions")
public class LoanEligibilityExtensionController extends AbstractCrudController<LoanEligibilityExtension, LoanEligibilityExtensionService> {

    private final LoanEligibilityExtensionService service;

    @Override
    protected LoanEligibilityExtensionService service() {
        return service;
    }
}
