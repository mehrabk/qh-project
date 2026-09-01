package ir.bank.qh.reference.controller;

import ir.bank.qh.reference.entity.LoanType;
import ir.bank.qh.reference.service.LoanTypeService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for LoanType.
 * Base path: /api/v1/product/reference/loan-types
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/reference/loan-types")
public class LoanTypeController extends AbstractCrudController<LoanType, LoanTypeService> {

    private final LoanTypeService service;

    @Override
    protected LoanTypeService service() {
        return service;
    }
}
