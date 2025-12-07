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
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategorySolutionKey implements Serializable {

    @Column(name = "ServiceID")
    private Integer solutionId;

    @Column(name = "CategoryID")
    private Integer categoryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategorySolutionKey that)) return false;
        return Objects.equals(solutionId, that.solutionId) &&
                Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(solutionId, categoryId);
    }
}
