package com.hexalyte.sf_service_application.controller;

import com.hexalyte.sf_service_application.model.Category;
import com.hexalyte.sf_service_application.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("category")
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
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id){
        return ResponseEntity.of(service.getCategoryById(id));
    }

    @PostMapping
    public ResponseEntity<Optional<Category>> addCategory(@RequestBody @Valid Category category){
        return new ResponseEntity<>(service.addCategory(category),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id,@RequestBody @Valid Category category){
        return ResponseEntity.of(service.updateCategory(id,category));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT,reason = "Category successfully deleted")
    public void deleteCategory(@PathVariable Long id){
        service.deleteCategory(id);
    }

}
