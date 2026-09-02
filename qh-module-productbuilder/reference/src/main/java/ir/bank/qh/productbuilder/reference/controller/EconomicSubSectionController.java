package ir.bank.qh.productbuilder.reference.controller;

import ir.bank.qh.productbuilder.reference.entity.EconomicSubSection;
import ir.bank.qh.productbuilder.reference.service.EconomicSubSectionService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for EconomicSubSection.
 * Base path: /api/v1/product/reference/economic-sub-sections
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/reference/economic-sub-sections")
public class EconomicSubSectionController extends AbstractCrudController<EconomicSubSection, EconomicSubSectionService> {

    private final EconomicSubSectionService service;

    @Override
    protected EconomicSubSectionService service() {
        return service;
    }
}
