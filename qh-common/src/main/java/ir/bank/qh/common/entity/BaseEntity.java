package ir.bank.qh.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Common audit + optimistic-locking base for every entity in the core banking
 * domain model.
 * <p>
 * The {@code version} field is deliberately left {@code null} in Java rather than
 * defaulted to {@code 0L}: the JPA provider must own the very first value on insert.
 * Spring Data JPA's default {@code isNew()} check for an entity carrying a
 * {@code @Version} field is "is version null" - a Java-side {@code = 0L} default
 * would make every brand-new entity look "already persisted", so
 * {@code repository.save(newEntity)} silently calls {@code merge()} instead of
 * {@code persist()}. {@code merge()} returns a *different* managed copy rather than
 * mutating the instance you passed in, so the caller's reference never gets its
 * generated id back - no exception, no SQL error, just a silently wrong reference.
 *
 * @author core-banking-party-model
 */
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEntity implements Serializable, Cloneable {

    @CreatedBy
    @Column(name = "CREATED_BY", nullable = true, updatable = false, length = 100)
    private String createdBy;

    @CreatedDate
    @Column(name = "CREATED_ON", nullable = true, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS")
    private Date createdOn;

    @LastModifiedBy
    @Column(name = "MODIFIED_BY", nullable = true, length = 100)
    private String modifiedBy;

    @LastModifiedDate
    @Column(name = "MODIFIED_ON", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS")
    private Date modifiedOn;

    /** Optimistic-locking token. Column name kept aligned with the reviewed models ({@code OPT_LOCK} / diagram's {@code RECORD_VERSION}). */
    @JsonIgnore
    @Version
    @Column(name = "RECORD_VERSION", nullable = true, columnDefinition = "integer DEFAULT 1")
    private Long version;

    @Generated
    public String getCreatedBy() {
        return this.createdBy;
    }

    @Generated
    public Date getCreatedOn() {
        return this.createdOn;
    }

    @Generated
    public String getModifiedBy() {
        return this.modifiedBy;
    }

    @Generated
    public Date getModifiedOn() {
        return this.modifiedOn;
    }

    @Generated
    public Long getVersion() {
        return this.version;
    }

    @Generated
    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }

    @Generated
    public void setCreatedOn(final Date createdOn) {
        this.createdOn = createdOn;
    }

    @Generated
    public void setModifiedBy(final String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Generated
    public void setModifiedOn(final Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    @JsonIgnore
    @Generated
    public void setVersion(final Long version) {
        this.version = version;
    }
}
