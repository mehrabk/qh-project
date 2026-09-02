package ir.bank.qh.productbuilder.reference.controller;

import ir.bank.qh.productbuilder.reference.entity.PatternOperation;
import ir.bank.qh.productbuilder.reference.service.PatternOperationService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for PatternOperation.
 * Base path: /api/v1/product/reference/pattern-operations
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/reference/pattern-operations")
public class PatternOperationController extends AbstractCrudController<PatternOperation, PatternOperationService> {

    private final PatternOperationService service;

    @Override
    protected PatternOperationService service() {
        return service;
    }
}
