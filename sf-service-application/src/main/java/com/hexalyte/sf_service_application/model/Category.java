package com.hexalyte.sf_service_application.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Data
public class Category {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "CategoryID")
        private Long categoryId;

        @Column(name = "Name", length = 50, nullable = false)
        @NotBlank(message = "Name cannot be null")
        @Length(max = 20,message = "Name cannot exceed 20 characters")
        private String name;

        @Column(name = "Description", columnDefinition = "TEXT")
        private String description;

        @JsonIgnoreProperties("category")
        @OneToMany(mappedBy = "category")
        private List<SolutionCategory> solutionCategories = new ArrayList<>();

}
