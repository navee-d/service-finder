package com.hexalyte.sf_service_application.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubCategorySolutionKey implements Serializable {

    @Column(name = "SubCategoryID")
    private Integer subCategoryId;

    @Column(name = "ServiceID")
    private Integer solutionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubCategorySolutionKey that)) return false;
        return Objects.equals(subCategoryId, that.subCategoryId) &&
                Objects.equals(solutionId, that.solutionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subCategoryId, solutionId);
    }
}