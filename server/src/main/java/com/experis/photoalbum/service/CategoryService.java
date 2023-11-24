package com.experis.photoalbum.service;

import com.experis.photoalbum.model.Category;
import com.experis.photoalbum.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findByPhotoId(Long id) {
        return categoryRepository.findCategoriesByPhotoId(id);
    }
}
