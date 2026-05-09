package com.turkcell.library_management.application.features.category.command.create;

import org.springframework.stereotype.Component;

import com.turkcell.library_management.application.features.category.mapper.CategoryMapper;
import com.turkcell.library_management.application.features.category.rule.CategoryBusinessRules;
import com.turkcell.library_management.core.mediator.cqrs.CommandHandler;
import com.turkcell.library_management.domain.Category;
import com.turkcell.library_management.persistence.repository.CategoryRepository;

@Component
public class CreateCategoryCommandHandler implements CommandHandler<CreateCategoryCommand, CreatedCategoryResponse> {
    private final CategoryRepository categoryRepository;
    private final CategoryBusinessRules categoryBusinessRules;
    private final CategoryMapper categoryMapper;

    public CreateCategoryCommandHandler(CategoryRepository categoryRepository,
            CategoryBusinessRules categoryBusinessRules, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryBusinessRules = categoryBusinessRules;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CreatedCategoryResponse handle(CreateCategoryCommand command) {
        categoryBusinessRules.categoryWithSameNameMustNotExist(command.name());

        Category category = categoryMapper.categoryFromCreateCommand(command);

        categoryRepository.save(category);

        return categoryMapper.createdCategoryResponseFromCategory(category);
    }
}
