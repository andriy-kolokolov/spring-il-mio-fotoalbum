package com.experis.photoalbum.controller.api.v1;

import com.experis.photoalbum.dto.CategoryDTO;
import com.experis.photoalbum.model.Category;
import com.experis.photoalbum.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryRestController {

    private final CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<List<CategoryDTO>> findPhotosByUserId(
            @PathVariable(value = "id") Long id
    ) {
        List<Category> categories = categoryService.findByPhotoId(id);

        if (categories == null || categories.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<CategoryDTO> CategoryDTOs = categories
                .stream()
                .map(CategoryDTO::fromCategory)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CategoryDTOs);
    }
}
