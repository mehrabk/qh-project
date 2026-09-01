package ir.bank.qh.reference.entity;

import ir.bank.qh.common.entity.BaseAuditableEntity;
import ir.bank.qh.reference.entity.EconomicSection;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Reference catalog of CBI economic sub-sections, e.g. PERSONAL_FINANCE, RETAIL_TRADE, MANUFACTURING.
 * Maps to table ECONOMIC_SUB_SECTION.
 */
@Getter
@Setter
@Entity
@Table(schema = "REFERENCE", name = "economic_sub_section")
public class EconomicSubSection extends BaseAuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "economic_section_id", nullable = false)
    private EconomicSection economicSection;


    @Column(name = "economic_sub_section_code", nullable = false, length = 30)
    private String economicSubSectionCode;


    @Column(name = "economic_sub_section_name", nullable = false, length = 100)
    private String economicSubSectionName;


    @Column(name = "economic_sub_section_cbi_code", length = 30)
    private String economicSubSectionCbiCode;


    @Column(name = "record_status_code", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
