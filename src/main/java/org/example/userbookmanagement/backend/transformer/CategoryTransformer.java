package org.example.userbookmanagement.backend.transformer;

import org.example.userbookmanagement.backend.bean.Category;
import org.example.userbookmanagement.backend.dto.CategoryDto;
import org.springframework.stereotype.Component;

@Component
public class CategoryTransformer extends AbstractTransformer<Category, CategoryDto> {
    @Override
    public Category toEntity(CategoryDto dto) {
        if (dto == null) {
            return null;
        } else {
            Category entity = new Category();
            entity.setId(dto.id());
            entity.setLabel(dto.label());
            return entity;
        }
    }

    @Override
    public CategoryDto toDto(Category entity) {
        if (entity == null) {
            return null;
        } else {
            return new CategoryDto(
                    entity.getId(),
                    entity.getLabel()
            );
        }
    }
}
