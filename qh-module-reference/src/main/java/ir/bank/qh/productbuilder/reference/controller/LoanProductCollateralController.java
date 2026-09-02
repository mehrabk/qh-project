package ir.bank.qh.productbuilder.reference.controller;

import ir.bank.qh.productbuilder.reference.entity.LoanProductCollateral;
import ir.bank.qh.productbuilder.reference.service.LoanProductCollateralService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for LoanProductCollateral.
 * Base path: /api/v1/product/reference/loan-product-collaterals
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/reference/loan-product-collaterals")
public class LoanProductCollateralController extends AbstractCrudController<LoanProductCollateral, LoanProductCollateralService> {

    private final LoanProductCollateralService service;

    @Override
    protected LoanProductCollateralService service() {
        return service;
    }
}
