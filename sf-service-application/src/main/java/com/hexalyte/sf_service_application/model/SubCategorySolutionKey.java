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
public class SubCategorySolutionKey implements Serializable {
    @Column(name = "sub_category_id")
    private Integer subCategoryId;

    @Column(name = "solution_id")
    private Integer solutionId;
}