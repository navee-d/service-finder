package com.hexalyte.sf_service_application.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
record SolutionCategoryKey(
        Long solutionId,
        Long categoryId
) implements Serializable {
}

@Entity
@Table(name = "m2m_solutioncategories")
public record SolutionCategory(
        @EmbeddedId
        SolutionCategoryKey id,

        @ManyToOne
        @MapsId("solutionId")
        @JoinColumn(name = "SolutionID")
        Solution solution,

        @ManyToOne
        @MapsId("categoryId")
        @JoinColumn(name = "CategoryID")
        Category category
) {
}
