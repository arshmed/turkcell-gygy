package com.turkcell.library_management.application.features.category.rule;

import org.springframework.stereotype.Component;

import com.turkcell.library_management.domain.Category;
import com.turkcell.library_management.persistence.repository.CategoryRepository;

@Component
public class CategoryBusinessRules {
    private final CategoryRepository categoryRepository;

    public CategoryBusinessRules(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void categoryWithSameNameMustNotExist(String name) {
        Category categoryWithSameName = categoryRepository.findByName(name).orElse(null);
        if (categoryWithSameName != null)
            throw new RuntimeException("Aynı isimde 2 kategori eklenemez");
    }
}
