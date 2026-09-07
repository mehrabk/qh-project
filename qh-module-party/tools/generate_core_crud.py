import os

TOOLS_DIR = os.path.dirname(os.path.abspath(__file__))
OUT_ROOT = os.path.normpath(os.path.join(TOOLS_DIR, '..', 'src/main/java/ir/bank/qh/party'))

# (EntityClassName, urlPath)
CORE = [
    ('PartyEntity', '/api/v1/party/parties'),
    ('PersonEntity', '/api/v1/party/persons'),
    ('OrganizationEntity', '/api/v1/party/organizations'),
    ('PartyMembershipEntity', '/api/v1/party/party-memberships'),
    ('PartyNameEntity', '/api/v1/party/party-names'),
    ('PartyIdentifierEntity', '/api/v1/party/party-identifiers'),
    ('AddressEntity', '/api/v1/party/addresses'),
    ('PartyAddressEntity', '/api/v1/party/party-addresses'),
    ('ContactPointEntity', '/api/v1/party/contact-points'),
    ('ContactPointAddressEntity', '/api/v1/party/contact-point-addresses'),
    ('PartyRoleEntity', '/api/v1/party/party-roles'),
    ('PartyGroupEntity', '/api/v1/party/party-groups'),
    ('PartyGroupMemberEntity', '/api/v1/party/party-group-members'),
    ('CustomerEntity', '/api/v1/party/customers'),
    ('OrganizationOfficerEntity', '/api/v1/party/organization-officers'),
    ('SignatureSpecimenEntity', '/api/v1/party/signature-specimens'),
    ('PartyIdentityReviewEntity', '/api/v1/party/party-identity-reviews'),
    ('AuditEventEntity', '/api/v1/party/audit-events'),
    ('AuditFieldChangeEntity', '/api/v1/party/audit-field-changes'),
]

def write(path, content):
    with open(path, 'w', encoding='utf-8') as f:
        f.write(content)

for entity_name, path in CORE:
    repo_name = entity_name + 'Repository'
    svc_name = entity_name + 'Service'
    ctrl_name = entity_name + 'Controller'

    repo = f'''package ir.bank.qh.party.repository;

import ir.bank.qh.party.entity.{entity_name};
import org.springframework.data.jpa.repository.JpaRepository;

public interface {repo_name} extends JpaRepository<{entity_name}, Long> {{
}}
'''
    write(f'{OUT_ROOT}/repository/{repo_name}.java', repo)

    svc = f'''package ir.bank.qh.party.service;

import ir.bank.qh.party.entity.{entity_name};
import ir.bank.qh.party.repository.{repo_name};
import ir.bank.qh.common.exception.ResourceNotFoundException;
import ir.bank.qh.common.service.JpaReferenceResolver;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Dedicated service for {entity_name}. Every operation is implemented directly
 * here (no shared AbstractCrudService base), matching every other Party service.
 */
@Service
@RequiredArgsConstructor
@Transactional("partyTransactionManager")
public class {svc_name} {{

    private final {repo_name} repository;

    @PersistenceContext(unitName = "party")
    private EntityManager entityManager;

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public List<{entity_name}> findAll() {{
        return repository.findAll();
    }}

    @Transactional(readOnly = true, transactionManager = "partyTransactionManager")
    public {entity_name} findById(Long id) {{
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("{entity_name}", id));
    }}

    public {entity_name} create({entity_name} entity) {{
        JpaReferenceResolver.resolveManyToOneReferences(entity, entityManager);
        return repository.save(entity);
    }}

    public {entity_name} update(Long id, {entity_name} incoming) {{
        JpaReferenceResolver.resolveManyToOneReferences(incoming, entityManager);
        {entity_name} existing = findById(id);
        BeanUtils.copyProperties(incoming, existing, JpaReferenceResolver.updateIgnoredProperties({entity_name}.class));
        return repository.save(existing);
    }}

    public void delete(Long id) {{
        {entity_name} existing = findById(id);
        repository.delete(existing);
    }}
}}
'''
    write(f'{OUT_ROOT}/service/{svc_name}.java', svc)

    ctrl = f'''package ir.bank.qh.party.controller;

import ir.bank.qh.party.entity.{entity_name};
import ir.bank.qh.party.service.{svc_name};
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
 * REST endpoints for {entity_name}. Base path: {path}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("{path}")
public class {ctrl_name} {{

    private final {svc_name} service;

    @GetMapping
    public ApiResponse<List<{entity_name}>> findAll() {{
        return ApiResponse.ok(service.findAll());
    }}

    @GetMapping("/{{id}}")
    public ApiResponse<{entity_name}> findById(@PathVariable Long id) {{
        return ApiResponse.ok(service.findById(id));
    }}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<{entity_name}> create(@Valid @RequestBody {entity_name} entity) {{
        return ApiResponse.ok(service.create(entity), "با موفقیت ایجاد شد");
    }}

    @PutMapping("/{{id}}")
    public ApiResponse<{entity_name}> update(@PathVariable Long id, @Valid @RequestBody {entity_name} entity) {{
        return ApiResponse.ok(service.update(id, entity), "با موفقیت به‌روزرسانی شد");
    }}

    @DeleteMapping("/{{id}}")
    public ApiResponse<Void> delete(@PathVariable Long id) {{
        service.delete(id);
        return ApiResponse.ok(null, "با موفقیت حذف شد");
    }}
}}
'''
    write(f'{OUT_ROOT}/controller/{ctrl_name}.java', ctrl)

print('Generated CRUD (repo/service/controller) for', len(CORE), 'core entities')
