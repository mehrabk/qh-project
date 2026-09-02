package ir.bank.qh.productbuilder.reference.controller;

import ir.bank.qh.productbuilder.reference.entity.PatternOperationLoanType;
import ir.bank.qh.productbuilder.reference.service.PatternOperationLoanTypeService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for PatternOperationLoanType.
 * Base path: /api/v1/product/reference/pattern-operation-loan-types
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/reference/pattern-operation-loan-types")
public class PatternOperationLoanTypeController extends AbstractCrudController<PatternOperationLoanType, PatternOperationLoanTypeService> {

    private final PatternOperationLoanTypeService service;

    @Override
    protected PatternOperationLoanTypeService service() {
        return service;
    }
}
