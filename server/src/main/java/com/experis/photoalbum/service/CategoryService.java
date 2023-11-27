package com.experis.photoalbum.service;

import com.experis.photoalbum.model.Category;
import com.experis.photoalbum.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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

    // find all
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    // get categories by ids
    public Set<Category> findByIds(List<Long> ids) {
        return categoryRepository.findCategoriesByIdIn(ids);
    }

    //find by id
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }
}
