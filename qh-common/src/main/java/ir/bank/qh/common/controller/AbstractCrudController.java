package ir.bank.qh.common.controller;

import ir.bank.qh.common.entity.BaseAuditableEntity;
import ir.bank.qh.common.service.AbstractCrudService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * Generic REST CRUD surface (GET list, GET one, POST, PUT, DELETE) shared by
 * every entity's controller in every module. Concrete controllers only need
 * to supply the service and the @RequestMapping path; extra domain-specific
 * endpoints can be added alongside these inherited ones.
 *
 * @param <T> entity type
 * @param <S> concrete service type
 */
public abstract class AbstractCrudController<T extends BaseAuditableEntity, S extends AbstractCrudService<T>> {

    protected abstract S service();

    @GetMapping
    public ApiResponse<List<T>> findAll() {
        return ApiResponse.ok(service().findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<T> findById(@PathVariable Long id) {
        return ApiResponse.ok(service().findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<T> create(@Valid @RequestBody T entity) {
        return ApiResponse.ok(service().create(entity), "با موفقیت ایجاد شد");
    }

    @PutMapping("/{id}")
    public ApiResponse<T> update(@PathVariable Long id, @Valid @RequestBody T entity) {
        return ApiResponse.ok(service().update(id, entity), "با موفقیت به‌روزرسانی شد");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service().delete(id);
        return ApiResponse.ok(null, "با موفقیت حذف شد");
    }
}
