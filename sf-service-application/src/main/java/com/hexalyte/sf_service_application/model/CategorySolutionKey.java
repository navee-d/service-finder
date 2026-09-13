package com.hexalyte.sf_service_application.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategorySolutionKey implements Serializable {
    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "solution_id")
    private Integer solutionId;
}