package ir.bank.qh.reference.controller;

import ir.bank.qh.reference.entity.FacilityOperationParameter;
import ir.bank.qh.reference.service.FacilityOperationParameterService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for FacilityOperationParameter.
 * Base path: /api/v1/product/reference/facility-operation-parameters
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/reference/facility-operation-parameters")
public class FacilityOperationParameterController extends AbstractCrudController<FacilityOperationParameter, FacilityOperationParameterService> {

    private final FacilityOperationParameterService service;

    @Override
    protected FacilityOperationParameterService service() {
        return service;
    }
}
