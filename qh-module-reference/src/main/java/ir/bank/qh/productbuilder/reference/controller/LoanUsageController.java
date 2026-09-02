package ir.bank.qh.productbuilder.reference.controller;

import ir.bank.qh.productbuilder.reference.entity.LoanUsage;
import ir.bank.qh.productbuilder.reference.service.LoanUsageService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for LoanUsage.
 * Base path: /api/v1/product/reference/loan-usages
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/reference/loan-usages")
public class LoanUsageController extends AbstractCrudController<LoanUsage, LoanUsageService> {

    private final LoanUsageService service;

    @Override
    protected LoanUsageService service() {
        return service;
    }
}
