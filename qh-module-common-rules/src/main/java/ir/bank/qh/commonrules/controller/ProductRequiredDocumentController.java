package ir.bank.qh.commonrules.controller;

import ir.bank.qh.commonrules.entity.ProductRequiredDocument;
import ir.bank.qh.commonrules.service.ProductRequiredDocumentService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for ProductRequiredDocument.
 * Base path: /api/v1/product/commonrules/product-required-documents
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/commonrules/product-required-documents")
public class ProductRequiredDocumentController extends AbstractCrudController<ProductRequiredDocument, ProductRequiredDocumentService> {

    private final ProductRequiredDocumentService service;

    @Override
    protected ProductRequiredDocumentService service() {
        return service;
    }
}
