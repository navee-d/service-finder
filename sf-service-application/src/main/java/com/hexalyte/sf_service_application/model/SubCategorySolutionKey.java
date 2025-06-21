package com.hexalyte.sf_service_application.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubCategorySolutionKey implements Serializable {
    private Integer subCategoryId;
    private Integer solutionId;
}
