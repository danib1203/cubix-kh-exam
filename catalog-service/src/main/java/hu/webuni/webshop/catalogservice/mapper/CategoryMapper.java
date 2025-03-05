package hu.webuni.webshop.catalogservice.mapper;

import hu.webuni.webshop.api.model.CategoryDto;
import hu.webuni.webshop.catalogservice.model.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category dtoToCategory(CategoryDto categoryDto);

    CategoryDto categoryToDto(Category category);

    List<CategoryDto> categoriesToDtos(List<Category> categories);

    List<CategoryDto> categoriesToDtos(Iterable<Category> all);
}
