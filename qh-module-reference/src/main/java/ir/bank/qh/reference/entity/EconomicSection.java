package ir.bank.qh.reference.entity;

import ir.bank.qh.common.entity.BaseAuditableEntity;
import ir.bank.qh.reference.entity.PlanType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Reference catalog of CBI economic sections, e.g. HOUSEHOLD, SERVICES, INDUSTRY.
 * Maps to table ECONOMIC_SECTION.
 */
@Getter
@Setter
@Entity
@Table(schema = "REFERENCE", name = "economic_section")
public class EconomicSection extends BaseAuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_type_id", nullable = false)
    private PlanType planType;


    @Column(name = "economic_section_code", nullable = false, length = 30)
    private String economicSectionCode;


    @Column(name = "economic_section_name", nullable = false, length = 100)
    private String economicSectionName;


    @Column(name = "economic_section_cbi_code", length = 30)
    private String economicSectionCbiCode;


    @Column(name = "record_status_code", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
