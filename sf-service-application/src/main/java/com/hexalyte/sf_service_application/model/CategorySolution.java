package com.hexalyte.sf_service_application.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "m2m_category_services")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class CategorySolution {
    @EmbeddedId
    private CategorySolutionKey id;

    @ManyToOne
    @MapsId("solutionId")
    @JoinColumn(name = "ServiceID", referencedColumnName = "ServiceID")
    @JsonIgnore
    private Solution solution;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "CategoryID", referencedColumnName = "CategoryID")
    @JsonIgnore
    private Category category;

}
