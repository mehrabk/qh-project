package ir.bank.qh.productbuilder.loan.controller;

import ir.bank.qh.productbuilder.loan.entity.LoanFinancialExtension;
import ir.bank.qh.productbuilder.loan.service.LoanFinancialExtensionService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for LoanFinancialExtension.
 * Base path: /api/v1/product/loan/loan-financial-extensions
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/loan/loan-financial-extensions")
public class LoanFinancialExtensionController extends AbstractCrudController<LoanFinancialExtension, LoanFinancialExtensionService> {

    private final LoanFinancialExtensionService service;

    @Override
    protected LoanFinancialExtensionService service() {
        return service;
    }
}
