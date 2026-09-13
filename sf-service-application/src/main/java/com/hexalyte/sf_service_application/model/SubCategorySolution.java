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
public class SubCategorySolution {

    @EmbeddedId
    private SubCategorySolutionKey id;

    @ManyToOne
    @MapsId("subCategoryId")
    @JoinColumn(name = "sub_category_id")
    private SubCategory subCategory;

    @ManyToOne
    @MapsId("solutionId")
    @JoinColumn(name = "solution_id")
    private Solution solution;
}