package com.hexalyte.sf_service_application.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolutionCategoryKey implements Serializable {
    private Long solutionId;
    private Long categoryId;
}
