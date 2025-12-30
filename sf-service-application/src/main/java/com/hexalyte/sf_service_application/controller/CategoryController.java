package com.hexalyte.sf_service_application.controller;

import com.hexalyte.sf_service_application.model.Category;
import com.hexalyte.sf_service_application.model.CategorySolution;
import com.hexalyte.sf_service_application.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("categories")
public class CategoryController {
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getCategories(){
        return new ResponseEntity<>(service.getCategories(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Integer id){
        return ResponseEntity.of(service.getCategoryById(id));
    }

    @GetMapping("category/services/{id}")
    private ResponseEntity<List<CategorySolution>> getCategoryServices(@PathVariable Integer id){
        return ResponseEntity.ofNullable(service.getCategoryServices(id));
    }

    @PostMapping
    public ResponseEntity<Category> addCategory(@RequestBody @Valid Category category){
        return ResponseEntity.of(service.addCategory(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Integer id,@RequestBody @Valid Category category){
        return ResponseEntity.of(service.updateCategory(id,category));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT,reason = "Category successfully deleted")
    public void deleteCategory(@PathVariable Integer id){
        service.deleteCategory(id);
    }

}
