package com.hexalyte.sf_service_application.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hexalyte.sf_service_application.model.deserializer.CategoryConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Table(name = "subcategories")
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "subCategoryId")
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SubcategoryID")
    private Integer subCategoryId;

    @ManyToOne
    @JoinColumn(name = "CategoryID", nullable = false)
    @JsonDeserialize(converter = CategoryConverter.class)
    private Category category;

    @Column(name = "Name", length = 50, nullable = false)
    @NotBlank(message = "Subcategory name cannot be null")
    @Length(max = 50, message = "Subcategory name cannot exceed 50 characters")
    private String name;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "subCategory")
    @JsonIgnore
    private List<Solution> solutions;

    @OneToMany(mappedBy = "subCategory", cascade = CascadeType.REMOVE)
    private List<SubCategorySolution> subCategorySolutions;

    @PreRemove
    private void preRemove() {
        solutions.forEach(
                solution -> solution.setSubCategory(null)
        );
    }

}
