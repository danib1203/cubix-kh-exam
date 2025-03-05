package hu.webuni.webshop.catalogservice.service;

import hu.webuni.webshop.catalogservice.mapper.CategoryMapper;
import hu.webuni.webshop.catalogservice.model.Category;
import hu.webuni.webshop.catalogservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;


    @Transactional
    public Category create(Category category) {
        if (Objects.isNull(category)) {
            return null;
        }
        if (categoryRepository.existsByName(category.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category with name: " + category.getName() + " already exists");
        }
        if (category.getId() != null && categoryRepository.existsById(category.getId())) {
            throw new IllegalArgumentException("Category with id " + category.getId() + " already " + "exists");
        }
        return save(category);
    }

    @Transactional
    public Category update(Category category) {
        if (Objects.isNull(category)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (findById(category.getId()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Category with id " + category.getId() + " not found");
        }
        return save(category);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(long id) {
        return categoryRepository.findById(id);
    }

    @Transactional
    public void delete(long id) {
        categoryRepository.deleteById(id);
    }

    private Category save(Category category) {
        return categoryRepository.save(category);
    }

}
