package ir.bank.qh.party.controller;

import ir.bank.qh.party.entity.SignatureSpecimenEntity;
import ir.bank.qh.party.service.SignatureSpecimenEntityService;
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
 * REST endpoints for SignatureSpecimenEntity. Every method is written out explicitly here
 * (no AbstractCrudController base), delegating to SignatureSpecimenEntityService.
 * Base path: /api/v1/party/signature-specimens
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/party/signature-specimens")
public class SignatureSpecimenEntityController {

    private final SignatureSpecimenEntityService service;

    @GetMapping
    public ApiResponse<List<SignatureSpecimenEntity>> findAll() {
        return ApiResponse.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<SignatureSpecimenEntity> findById(@PathVariable Long id) {
        return ApiResponse.ok(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<SignatureSpecimenEntity> create(@Valid @RequestBody SignatureSpecimenEntity entity) {
        return ApiResponse.ok(service.create(entity), "با موفقیت ایجاد شد");
    }

    @PutMapping("/{id}")
    public ApiResponse<SignatureSpecimenEntity> update(@PathVariable Long id, @Valid @RequestBody SignatureSpecimenEntity entity) {
        return ApiResponse.ok(service.update(id, entity), "با موفقیت به‌روزرسانی شد");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.ok(null, "با موفقیت حذف شد");
    }
}
