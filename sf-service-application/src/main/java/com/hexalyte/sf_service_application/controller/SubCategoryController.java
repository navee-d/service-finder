package com.hexalyte.sf_service_application.controller;

import com.hexalyte.sf_service_application.model.SubCategory;
import com.hexalyte.sf_service_application.model.SubCategorySolution;
import com.hexalyte.sf_service_application.service.SubCategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("subcategories")
public class SubCategoryController {

    private final SubCategoryService service;

    public SubCategoryController(SubCategoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SubCategory>> getSubCategories() {
        return ResponseEntity.ofNullable(service.getSubCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubCategory> getSubCategoryById(@PathVariable Integer id) {
        return ResponseEntity.of(service.getSubCategoryById(id));
    }

    @GetMapping("subcategory/services/{id}")
    public ResponseEntity<List<SubCategorySolution>> getSubCategoryServices(@PathVariable Integer id){
        return ResponseEntity.ofNullable(service.getSubCategoryServices(id));
    }

    @PostMapping
    public ResponseEntity<Optional<SubCategory>> addSubCategory(@RequestBody @Valid SubCategory subCategory) {
        return new ResponseEntity<>(service.addSubCategory(subCategory), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubCategory> updateSubCategory(@RequestBody @Valid SubCategory subCategory, @PathVariable Integer id) {
        return ResponseEntity.of(service.updateSubCategory(subCategory, id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT,reason = "Subcategory deleted successfully")
    public void deleteSubCategory(@PathVariable Integer id){
        service.deleteSubCategory(id);
    }
}
