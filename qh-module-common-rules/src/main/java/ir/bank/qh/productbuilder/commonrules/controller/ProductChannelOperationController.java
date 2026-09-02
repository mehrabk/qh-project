package ir.bank.qh.productbuilder.commonrules.controller;

import ir.bank.qh.productbuilder.commonrules.entity.ProductChannelOperation;
import ir.bank.qh.productbuilder.commonrules.service.ProductChannelOperationService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for ProductChannelOperation.
 * Base path: /api/v1/product/commonrules/product-channel-operations
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/commonrules/product-channel-operations")
public class ProductChannelOperationController extends AbstractCrudController<ProductChannelOperation, ProductChannelOperationService> {

    private final ProductChannelOperationService service;

    @Override
    protected ProductChannelOperationService service() {
        return service;
    }
}
