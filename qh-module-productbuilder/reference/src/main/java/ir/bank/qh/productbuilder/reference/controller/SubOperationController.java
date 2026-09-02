package ir.bank.qh.productbuilder.reference.controller;

import ir.bank.qh.productbuilder.reference.entity.SubOperation;
import ir.bank.qh.productbuilder.reference.service.SubOperationService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for SubOperation.
 * Base path: /api/v1/product/reference/sub-operations
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/reference/sub-operations")
public class SubOperationController extends AbstractCrudController<SubOperation, SubOperationService> {

    private final SubOperationService service;

    @Override
    protected SubOperationService service() {
        return service;
    }
}
