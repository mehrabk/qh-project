package ir.bank.qh.reference.entity;

import ir.bank.qh.common.entity.BaseEntity;
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
@Table(schema = "PRODUCTBUILDER", name = "economic_sub_section")
public class EconomicSubSection extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ECONOMIC_SECTION_ID", nullable = false)
    private EconomicSection economicSection;


    @Column(name = "ECONOMIC_SUB_SECTION_CODE", nullable = false, length = 30)
    private String economicSubSectionCode;


    @Column(name = "ECONOMIC_SUB_SECTION_NAME", nullable = false, length = 100)
    private String economicSubSectionName;


    @Column(name = "ECONOMIC_SUB_SECTION_CBI_CODE", length = 30)
    private String economicSubSectionCbiCode;


    @Column(name = "RECORD_STATUS_CODE", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
