package com.hexalyte.sf_service_application.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "m2m_solutioncategories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolutionCategory {
    @EmbeddedId
    private SolutionCategoryKey id;

    @ManyToOne
    @MapsId("solutionId")
    @JoinColumn(name = "SolutionID")
    @JsonIgnoreProperties("solutionCategories")
    private Solution solution;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "CategoryID")
    @JsonIgnoreProperties("solutionCategories")
    private Category category;

}
