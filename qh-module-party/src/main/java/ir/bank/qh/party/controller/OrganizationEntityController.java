package ir.bank.qh.party.controller;

import ir.bank.qh.party.entity.OrganizationEntity;
import ir.bank.qh.party.service.OrganizationEntityService;
import ir.bank.qh.common.controller.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST endpoints for OrganizationEntity. Every method is written out explicitly here
 * (no AbstractCrudController base), delegating to OrganizationEntityService.
 * Base path: /api/v1/party/organizations
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/party/organizations")
public class OrganizationEntityController {

    private final OrganizationEntityService service;

    @GetMapping
    public ApiResponse<List<OrganizationEntity>> findAll() {
        return ApiResponse.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<OrganizationEntity> findById(@PathVariable Long id) {
        return ApiResponse.ok(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<OrganizationEntity> create(@Valid @RequestBody OrganizationEntity entity) {
        return ApiResponse.ok(service.create(entity), "با موفقیت ایجاد شد");
    }

    @PutMapping("/{id}")
    public ApiResponse<OrganizationEntity> update(@PathVariable Long id, @Valid @RequestBody OrganizationEntity entity) {
        return ApiResponse.ok(service.update(id, entity), "با موفقیت به‌روزرسانی شد");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.ok(null, "با موفقیت حذف شد");
    }
}
