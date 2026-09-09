package ir.bank.qh.party.core.controller;

import ir.bank.qh.party.core.entity.PartyGroupMemberEntity;
import ir.bank.qh.party.core.service.PartyGroupMemberEntityService;
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
 * REST endpoints for PartyGroupMemberEntity. Base path: /api/v1/party/party-group-members
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/party/party-group-members")
public class PartyGroupMemberEntityController {

    private final PartyGroupMemberEntityService service;

    @GetMapping
    public ApiResponse<List<PartyGroupMemberEntity>> findAll() {
        return ApiResponse.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<PartyGroupMemberEntity> findById(@PathVariable Long id) {
        return ApiResponse.ok(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<PartyGroupMemberEntity> create(@Valid @RequestBody PartyGroupMemberEntity entity) {
        return ApiResponse.ok(service.create(entity), "با موفقیت ایجاد شد");
    }

    @PutMapping("/{id}")
    public ApiResponse<PartyGroupMemberEntity> update(@PathVariable Long id, @Valid @RequestBody PartyGroupMemberEntity entity) {
        return ApiResponse.ok(service.update(id, entity), "با موفقیت به‌روزرسانی شد");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.ok(null, "با موفقیت حذف شد");
    }
}
