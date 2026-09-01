package ir.bank.qh.deposit.controller;

import ir.bank.qh.deposit.entity.DepositProductWithdrawalMedia;
import ir.bank.qh.deposit.service.DepositProductWithdrawalMediaService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for DepositProductWithdrawalMedia.
 * Base path: /api/v1/product/deposit/deposit-product-withdrawal-medias
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/deposit/deposit-product-withdrawal-medias")
public class DepositProductWithdrawalMediaController extends AbstractCrudController<DepositProductWithdrawalMedia, DepositProductWithdrawalMediaService> {

    private final DepositProductWithdrawalMediaService service;

    @Override
    protected DepositProductWithdrawalMediaService service() {
        return service;
    }
}
