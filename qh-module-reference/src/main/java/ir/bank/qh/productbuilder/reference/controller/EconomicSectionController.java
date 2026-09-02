package ir.bank.qh.productbuilder.reference.controller;

import ir.bank.qh.productbuilder.reference.entity.EconomicSection;
import ir.bank.qh.productbuilder.reference.service.EconomicSectionService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for EconomicSection.
 * Base path: /api/v1/product/reference/economic-sections
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/reference/economic-sections")
public class EconomicSectionController extends AbstractCrudController<EconomicSection, EconomicSectionService> {

    private final EconomicSectionService service;

    @Override
    protected EconomicSectionService service() {
        return service;
    }
}
