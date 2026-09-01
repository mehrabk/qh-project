package ir.bank.qh.deposit.controller;

import ir.bank.qh.deposit.entity.DepositProductProfile;
import ir.bank.qh.deposit.service.DepositProductProfileService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for DepositProductProfile.
 * Base path: /api/v1/product/deposit/deposit-product-profiles
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/deposit/deposit-product-profiles")
public class DepositProductProfileController extends AbstractCrudController<DepositProductProfile, DepositProductProfileService> {

    private final DepositProductProfileService service;

    @Override
    protected DepositProductProfileService service() {
        return service;
    }
}
