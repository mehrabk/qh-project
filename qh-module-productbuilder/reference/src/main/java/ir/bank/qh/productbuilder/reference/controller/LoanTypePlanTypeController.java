package ir.bank.qh.productbuilder.reference.controller;

import ir.bank.qh.productbuilder.reference.entity.LoanTypePlanType;
import ir.bank.qh.productbuilder.reference.service.LoanTypePlanTypeService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for LoanTypePlanType.
 * Base path: /api/v1/product/reference/loan-type-plan-types
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/reference/loan-type-plan-types")
public class LoanTypePlanTypeController extends AbstractCrudController<LoanTypePlanType, LoanTypePlanTypeService> {

    private final LoanTypePlanTypeService service;

    @Override
    protected LoanTypePlanTypeService service() {
        return service;
    }
}
