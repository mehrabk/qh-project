package ir.bank.qh.reference.controller;

import ir.bank.qh.reference.entity.PatternOperationDetail;
import ir.bank.qh.reference.service.PatternOperationDetailService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for PatternOperationDetail.
 * Base path: /api/v1/product/reference/pattern-operation-details
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/reference/pattern-operation-details")
public class PatternOperationDetailController extends AbstractCrudController<PatternOperationDetail, PatternOperationDetailService> {

    private final PatternOperationDetailService service;

    @Override
    protected PatternOperationDetailService service() {
        return service;
    }
}
