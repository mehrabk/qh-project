package ir.bank.qh.loan.controller;

import ir.bank.qh.loan.entity.LoanProductProfile;
import ir.bank.qh.loan.service.LoanProductProfileService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for LoanProductProfile.
 * Base path: /api/v1/product/loan/loan-product-profiles
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/loan/loan-product-profiles")
public class LoanProductProfileController extends AbstractCrudController<LoanProductProfile, LoanProductProfileService> {

    private final LoanProductProfileService service;

    @Override
    protected LoanProductProfileService service() {
        return service;
    }
}
