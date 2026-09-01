package ir.bank.qh.reference.controller;

import ir.bank.qh.reference.entity.DocumentType;
import ir.bank.qh.reference.service.DocumentTypeService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for DocumentType.
 * Base path: /api/v1/product/reference/document-types
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/reference/document-types")
public class DocumentTypeController extends AbstractCrudController<DocumentType, DocumentTypeService> {

    private final DocumentTypeService service;

    @Override
    protected DocumentTypeService service() {
        return service;
    }
}
