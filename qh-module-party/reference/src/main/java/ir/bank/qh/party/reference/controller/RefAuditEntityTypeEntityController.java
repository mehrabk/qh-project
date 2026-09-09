package ir.bank.qh.party.reference.controller;

import ir.bank.qh.party.reference.entity.RefAuditEntityTypeEntity;
import ir.bank.qh.party.reference.service.RefAuditEntityTypeEntityService;
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
 * REST endpoints for RefAuditEntityTypeEntity. Base path: /api/v1/party/ref-audit-entity-types
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/party/ref-audit-entity-types")
public class RefAuditEntityTypeEntityController {

    private final RefAuditEntityTypeEntityService service;

    @GetMapping
    public ApiResponse<List<RefAuditEntityTypeEntity>> findAll() {
        return ApiResponse.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<RefAuditEntityTypeEntity> findById(@PathVariable String id) {
        return ApiResponse.ok(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RefAuditEntityTypeEntity> create(@Valid @RequestBody RefAuditEntityTypeEntity entity) {
        return ApiResponse.ok(service.create(entity), "با موفقیت ایجاد شد");
    }

    @PutMapping("/{id}")
    public ApiResponse<RefAuditEntityTypeEntity> update(@PathVariable String id, @Valid @RequestBody RefAuditEntityTypeEntity entity) {
        return ApiResponse.ok(service.update(id, entity), "با موفقیت به‌روزرسانی شد");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ApiResponse.ok(null, "با موفقیت حذف شد");
    }
}
