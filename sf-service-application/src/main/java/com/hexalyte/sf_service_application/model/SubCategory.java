package com.hexalyte.sf_service_application.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hexalyte.sf_service_application.model.deserializer.CategoryConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subcategories")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "subCategoryId")
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SubcategoryID")
    private Integer subCategoryId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoryID", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonDeserialize(converter = CategoryConverter.class)
    private Category category;

    @Column(name = "Name", length = 50, nullable = false)
    @NotBlank(message = "Subcategory name cannot be null")
    @Length(max = 50, message = "Subcategory name cannot exceed 50 characters")
    private String name;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;

    // Services relationship
    @OneToMany(mappedBy = "subCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore // prevent lazy-loading serialization issues
    @Builder.Default
    private List<Service> services = new ArrayList<>();

    // SubCategorySolutions relationship
    @OneToMany(mappedBy = "subCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Builder.Default
    private List<SubCategorySolution> subCategorySolutions = new ArrayList<>();

    @PreRemove
    private void preRemove() {
        if (services != null) {
            services.forEach(service -> service.setSubCategory(null));
        }
    }
}
