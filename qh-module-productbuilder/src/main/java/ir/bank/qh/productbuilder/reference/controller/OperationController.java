package ir.bank.qh.productbuilder.reference.controller;

import ir.bank.qh.productbuilder.reference.entity.Operation;
import ir.bank.qh.productbuilder.reference.service.OperationService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for Operation.
 * Base path: /api/v1/product/reference/operations
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/reference/operations")
public class OperationController extends AbstractCrudController<Operation, OperationService> {

    private final OperationService service;

    @Override
    protected OperationService service() {
        return service;
    }
}
