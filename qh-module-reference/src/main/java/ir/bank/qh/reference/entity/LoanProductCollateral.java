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
@Table(schema = "REFERENCE", name = "loan_product_collateral")
public class LoanProductCollateral extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;



    @Column(name = "product_collateral_code", nullable = false, unique = true, length = 30)
    private String productCollateralCode;


    @Column(name = "product_collateral_name", nullable = false, length = 150)
    private String productCollateralName;


    @Column(name = "collateral_category_code", length = 30)
    private String collateralCategoryCode;


    @Column(name = "description", length = 500)
    private String description;


    @Column(name = "record_status_code", nullable = false, length = 20)
    private String recordStatusCode = "ACTIVE";
}
