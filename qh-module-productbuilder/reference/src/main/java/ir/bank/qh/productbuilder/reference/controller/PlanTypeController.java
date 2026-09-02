package ir.bank.qh.productbuilder.reference.controller;

import ir.bank.qh.productbuilder.reference.entity.PlanType;
import ir.bank.qh.productbuilder.reference.service.PlanTypeService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for PlanType.
 * Base path: /api/v1/product/reference/plan-types
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/reference/plan-types")
public class PlanTypeController extends AbstractCrudController<PlanType, PlanTypeService> {

    private final PlanTypeService service;

    @Override
    protected PlanTypeService service() {
        return service;
    }
}
