package ir.bank.qh.party.reference.controller;

import ir.bank.qh.party.reference.entity.RefContactAddressAssocTypeEntity;
import ir.bank.qh.party.reference.service.RefContactAddressAssocTypeEntityService;
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
 * REST endpoints for RefContactAddressAssocTypeEntity. Base path: /api/v1/party/ref-contact-address-assoc-types
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/party/ref-contact-address-assoc-types")
public class RefContactAddressAssocTypeEntityController {

    private final RefContactAddressAssocTypeEntityService service;

    @GetMapping
    public ApiResponse<List<RefContactAddressAssocTypeEntity>> findAll() {
        return ApiResponse.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<RefContactAddressAssocTypeEntity> findById(@PathVariable String id) {
        return ApiResponse.ok(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RefContactAddressAssocTypeEntity> create(@Valid @RequestBody RefContactAddressAssocTypeEntity entity) {
        return ApiResponse.ok(service.create(entity), "با موفقیت ایجاد شد");
    }

    @PutMapping("/{id}")
    public ApiResponse<RefContactAddressAssocTypeEntity> update(@PathVariable String id, @Valid @RequestBody RefContactAddressAssocTypeEntity entity) {
        return ApiResponse.ok(service.update(id, entity), "با موفقیت به‌روزرسانی شد");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ApiResponse.ok(null, "با موفقیت حذف شد");
    }
}
