package com.hexalyte.sf_service_application.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategorySolution {

    @EmbeddedId
    private CategorySolutionKey id;

    @ManyToOne
    @MapsId("categoryId") // Maps the categoryId from the key to this entity
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @MapsId("solutionId") // Maps the solutionId from the key to this entity
    @JoinColumn(name = "solution_id")
    private Solution solution;
}