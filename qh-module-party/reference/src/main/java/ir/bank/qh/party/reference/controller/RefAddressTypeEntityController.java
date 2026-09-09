package ir.bank.qh.party.reference.controller;

import ir.bank.qh.party.reference.entity.RefAddressTypeEntity;
import ir.bank.qh.party.reference.service.RefAddressTypeEntityService;
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
 * REST endpoints for RefAddressTypeEntity. Base path: /api/v1/party/ref-address-types
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/party/ref-address-types")
public class RefAddressTypeEntityController {

    private final RefAddressTypeEntityService service;

    @GetMapping
    public ApiResponse<List<RefAddressTypeEntity>> findAll() {
        return ApiResponse.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<RefAddressTypeEntity> findById(@PathVariable String id) {
        return ApiResponse.ok(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RefAddressTypeEntity> create(@Valid @RequestBody RefAddressTypeEntity entity) {
        return ApiResponse.ok(service.create(entity), "با موفقیت ایجاد شد");
    }

    @PutMapping("/{id}")
    public ApiResponse<RefAddressTypeEntity> update(@PathVariable String id, @Valid @RequestBody RefAddressTypeEntity entity) {
        return ApiResponse.ok(service.update(id, entity), "با موفقیت به‌روزرسانی شد");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ApiResponse.ok(null, "با موفقیت حذف شد");
    }
}
