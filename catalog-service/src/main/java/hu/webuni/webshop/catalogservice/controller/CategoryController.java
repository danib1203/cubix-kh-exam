package hu.webuni.webshop.catalogservice.controller;

import hu.webuni.webshop.api.CategoryControllerApi;
import hu.webuni.webshop.api.ProductControllerApi;
import hu.webuni.webshop.api.model.CategoryDto;
import hu.webuni.webshop.api.model.ProductDto;
import hu.webuni.webshop.catalogservice.mapper.CategoryMapper;
import hu.webuni.webshop.catalogservice.mapper.ProductMapper;
import hu.webuni.webshop.catalogservice.model.Category;
import hu.webuni.webshop.catalogservice.model.Product;
import hu.webuni.webshop.catalogservice.service.CategoryService;
import hu.webuni.webshop.catalogservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CategoryController implements CategoryControllerApi {


    private final NativeWebRequest webRequest;
    private final PageableHandlerMethodArgumentResolver pageableResolver;
    // private final HistoryDataMapper historyDataMapper;
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.of(webRequest);
    }

    @Override
    public ResponseEntity<CategoryDto> createCategory(CategoryDto categoryDto) {
        Category category = categoryService.create(categoryMapper.dtoToCategory(categoryDto));
        return ResponseEntity.ok(categoryMapper.categoryToDto(category));
    }
}
