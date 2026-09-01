package ir.bank.qh.deposit.controller;

import ir.bank.qh.deposit.entity.DepositProductClosurePrecheck;
import ir.bank.qh.deposit.service.DepositProductClosurePrecheckService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for DepositProductClosurePrecheck.
 * Base path: /api/v1/product/deposit/deposit-product-closure-prechecks
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/deposit/deposit-product-closure-prechecks")
public class DepositProductClosurePrecheckController extends AbstractCrudController<DepositProductClosurePrecheck, DepositProductClosurePrecheckService> {

    private final DepositProductClosurePrecheckService service;

    @Override
    protected DepositProductClosurePrecheckService service() {
        return service;
    }
}
