package ir.bank.qh.productbuilder.reference.entity;

import ir.bank.qh.common.entity.BaseEntity;
import ir.bank.qh.common.enums.RecordStatus;
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
@Table(schema = "PRODUCTBUILDER", name = "economic_section")
public class EconomicSection extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAN_TYPE_ID", nullable = false)
    private PlanType planType;


    @Column(name = "ECONOMIC_SECTION_CODE", nullable = false, length = 30)
    private String economicSectionCode;


    @Column(name = "ECONOMIC_SECTION_NAME", nullable = false, length = 100)
    private String economicSectionName;


    @Column(name = "ECONOMIC_SECTION_CBI_CODE", length = 30)
    private String economicSectionCbiCode;


    @Column(name = "RECORD_STATUS_CODE", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private RecordStatus recordStatusCode = RecordStatus.ACTIVE;
}
