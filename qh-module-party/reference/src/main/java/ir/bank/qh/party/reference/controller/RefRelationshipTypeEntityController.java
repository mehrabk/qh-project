package ir.bank.qh.party.reference.controller;

import ir.bank.qh.party.reference.entity.RefRelationshipTypeEntity;
import ir.bank.qh.party.reference.service.RefRelationshipTypeEntityService;
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
 * REST endpoints for RefRelationshipTypeEntity. Base path: /api/v1/party/ref-relationship-types
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/party/ref-relationship-types")
public class RefRelationshipTypeEntityController {

    private final RefRelationshipTypeEntityService service;

    @GetMapping
    public ApiResponse<List<RefRelationshipTypeEntity>> findAll() {
        return ApiResponse.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<RefRelationshipTypeEntity> findById(@PathVariable String id) {
        return ApiResponse.ok(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RefRelationshipTypeEntity> create(@Valid @RequestBody RefRelationshipTypeEntity entity) {
        return ApiResponse.ok(service.create(entity), "با موفقیت ایجاد شد");
    }

    @PutMapping("/{id}")
    public ApiResponse<RefRelationshipTypeEntity> update(@PathVariable String id, @Valid @RequestBody RefRelationshipTypeEntity entity) {
        return ApiResponse.ok(service.update(id, entity), "با موفقیت به‌روزرسانی شد");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ApiResponse.ok(null, "با موفقیت حذف شد");
    }
}
