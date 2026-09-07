package ir.bank.qh.party.controller;

import ir.bank.qh.party.entity.RefResidenceStatusEntity;
import ir.bank.qh.party.service.RefResidenceStatusEntityService;
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
 * REST endpoints for RefResidenceStatusEntity. Base path: /api/v1/party/ref-residence-statuses
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/party/ref-residence-statuses")
public class RefResidenceStatusEntityController {

    private final RefResidenceStatusEntityService service;

    @GetMapping
    public ApiResponse<List<RefResidenceStatusEntity>> findAll() {
        return ApiResponse.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<RefResidenceStatusEntity> findById(@PathVariable String id) {
        return ApiResponse.ok(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RefResidenceStatusEntity> create(@Valid @RequestBody RefResidenceStatusEntity entity) {
        return ApiResponse.ok(service.create(entity), "با موفقیت ایجاد شد");
    }

    @PutMapping("/{id}")
    public ApiResponse<RefResidenceStatusEntity> update(@PathVariable String id, @Valid @RequestBody RefResidenceStatusEntity entity) {
        return ApiResponse.ok(service.update(id, entity), "با موفقیت به‌روزرسانی شد");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ApiResponse.ok(null, "با موفقیت حذف شد");
    }
}
