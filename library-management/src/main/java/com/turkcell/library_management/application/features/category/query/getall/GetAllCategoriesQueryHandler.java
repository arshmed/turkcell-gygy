package com.turkcell.library_management.application.features.category.query.getall;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.turkcell.library_management.application.features.category.mapper.CategoryMapper;
import com.turkcell.library_management.core.mediator.cqrs.QueryHandler;
import com.turkcell.library_management.domain.Category;
import com.turkcell.library_management.persistence.repository.CategoryRepository;

@Component
public class GetAllCategoriesQueryHandler implements QueryHandler<GetAllCategoriesQuery, Page<GetAllCategoriesResponse>> {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public GetAllCategoriesQueryHandler(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Page<GetAllCategoriesResponse> handle(GetAllCategoriesQuery query) {
        Pageable pageable = PageRequest.of(query.pageNumber(), query.pageSize());
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories.map(categoryMapper::getAllCategoriesResponseFromCategory);
    }
}
