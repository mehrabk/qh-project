package ir.bank.qh.party.controller;

import ir.bank.qh.party.entity.PartyNameEntity;
import ir.bank.qh.party.service.PartyNameEntityService;
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
 * REST endpoints for PartyNameEntity. Every method is written out explicitly here
 * (no AbstractCrudController base), delegating to PartyNameEntityService.
 * Base path: /api/v1/party/party-names
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/party/party-names")
public class PartyNameEntityController {

    private final PartyNameEntityService service;

    @GetMapping
    public ApiResponse<List<PartyNameEntity>> findAll() {
        return ApiResponse.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<PartyNameEntity> findById(@PathVariable Long id) {
        return ApiResponse.ok(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<PartyNameEntity> create(@Valid @RequestBody PartyNameEntity entity) {
        return ApiResponse.ok(service.create(entity), "با موفقیت ایجاد شد");
    }

    @PutMapping("/{id}")
    public ApiResponse<PartyNameEntity> update(@PathVariable Long id, @Valid @RequestBody PartyNameEntity entity) {
        return ApiResponse.ok(service.update(id, entity), "با موفقیت به‌روزرسانی شد");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.ok(null, "با موفقیت حذف شد");
    }
}
