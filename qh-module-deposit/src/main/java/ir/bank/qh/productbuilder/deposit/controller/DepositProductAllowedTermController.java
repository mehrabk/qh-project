package ir.bank.qh.productbuilder.deposit.controller;

import ir.bank.qh.productbuilder.deposit.entity.DepositProductAllowedTerm;
import ir.bank.qh.productbuilder.deposit.service.DepositProductAllowedTermService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for DepositProductAllowedTerm.
 * Base path: /api/v1/product/deposit/deposit-product-allowed-terms
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/deposit/deposit-product-allowed-terms")
public class DepositProductAllowedTermController extends AbstractCrudController<DepositProductAllowedTerm, DepositProductAllowedTermService> {

    private final DepositProductAllowedTermService service;

    @Override
    protected DepositProductAllowedTermService service() {
        return service;
    }
}
