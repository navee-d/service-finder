package com.hexalyte.sf_service_application.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jdk.jshell.Snippet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subcategory_services")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SubCategorySolution {

    @EmbeddedId
    private SubCategorySolutionKey id;

    @ManyToOne
    @MapsId("solutionId")
    @JoinColumn(name = "ServiceID")
    private Service service;

    @ManyToOne
    @MapsId("subCategoryId")
    @JoinColumn(name = "SubCategoryID")
    private SubCategory subCategory;

  
}