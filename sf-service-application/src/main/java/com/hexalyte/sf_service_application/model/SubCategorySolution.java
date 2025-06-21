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
@Table(name = "m2m_subcategory_services")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SubCategorySolution {

    @EmbeddedId
    private SubCategorySolutionKey id;

    @ManyToOne
    @JoinColumn(name = "SubCategoryID")
    @MapsId("subCategoryId")
    @JsonIgnore
    private SubCategory subCategory;

    @ManyToOne
    @JoinColumn(name = "ServiceID")
    @MapsId("solutionId")
    @JsonIgnore
    private Solution solution;
}
