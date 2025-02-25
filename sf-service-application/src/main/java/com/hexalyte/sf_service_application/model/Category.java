package com.hexalyte.sf_service_application.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "categories")
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

        public Long getCategoryId() {
                return categoryId;
        }

        public void setCategoryId(Long categoryId) {
                this.categoryId = categoryId;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }
}
