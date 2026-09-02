package ir.bank.qh.productbuilder.commonrules.controller;

import ir.bank.qh.productbuilder.commonrules.entity.ProductRequiredInquiry;
import ir.bank.qh.productbuilder.commonrules.service.ProductRequiredInquiryService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for ProductRequiredInquiry.
 * Base path: /api/v1/product/commonrules/product-required-inquirys
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/commonrules/product-required-inquirys")
public class ProductRequiredInquiryController extends AbstractCrudController<ProductRequiredInquiry, ProductRequiredInquiryService> {

    private final ProductRequiredInquiryService service;

    @Override
    protected ProductRequiredInquiryService service() {
        return service;
    }
}
