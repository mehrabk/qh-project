package ir.bank.qh.party.controller;

import ir.bank.qh.party.entity.PartyEntity;
import ir.bank.qh.party.service.PartyEntityService;
import ir.bank.qh.common.controller.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Read-only, polymorphic access to the Party hierarchy (returns actual
 * PersonEntity/OrganizationEntity rows through the abstract PartyEntity type -
 * Jackson serializes each one using its real runtime class).
 * Base path: /api/v1/party/parties
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/party/parties")
public class PartyEntityController {

    private final PartyEntityService service;

    @GetMapping
    public ApiResponse<List<PartyEntity>> findAll() {
        return ApiResponse.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<PartyEntity> findById(@PathVariable Long id) {
        return ApiResponse.ok(service.findById(id));
    }
}
