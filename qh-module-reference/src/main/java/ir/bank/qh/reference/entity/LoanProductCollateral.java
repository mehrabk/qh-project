package ir.bank.qh.reference.entity;

import ir.bank.qh.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Reference catalog of collateral patterns, e.g. GUARANTOR, CASH_DEPOSIT, PROPERTY.
 * Maps to table LOAN_PRODUCT_COLLATERAL.
 */
@Getter
@Setter
@Entity
@Table(schema = "PRODUCTBUILDER", name = "loan_product_collateral")
public class LoanProductCollateral extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;



    @Column(name = "PRODUCT_COLLATERAL_CODE", nullable = false, unique = true, length = 30)
    private String productCollateralCode;


    @Column(name = "PRODUCT_COLLATERAL_NAME", nullable = false, length = 150)
    private String productCollateralName;


    @Column(name = "COLLATERAL_CATEGORY_CODE", length = 30)
    private String collateralCategoryCode;


    @Column(name = "DESCRIPTION", length = 500)
    private String description;


    @Column(name = "RECORD_STATUS_CODE", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
