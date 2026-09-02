package ir.bank.qh.productbuilder.commonrules.controller;

import ir.bank.qh.productbuilder.commonrules.entity.ProductOrgScope;
import ir.bank.qh.productbuilder.commonrules.service.ProductOrgScopeService;
import ir.bank.qh.common.controller.AbstractCrudController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoints for ProductOrgScope.
 * Base path: /api/v1/product/commonrules/product-org-scopes
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/commonrules/product-org-scopes")
public class ProductOrgScopeController extends AbstractCrudController<ProductOrgScope, ProductOrgScopeService> {

    private final ProductOrgScopeService service;

    @Override
    protected ProductOrgScopeService service() {
        return service;
    }
}
