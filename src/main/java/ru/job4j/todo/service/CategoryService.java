package ru.job4j.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.CategoryDbStore;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    public final CategoryDbStore categoryDbStore;

    public Category create(Category category) {
        return categoryDbStore.create(category);
    }

    public List<Category> findAll() {
        return categoryDbStore.findAll();
    }

    public Category findById(int id) {
        return categoryDbStore.findById(id);
    }

    public Category update(int id, Category category) {
        return categoryDbStore.update(id, category);
    }

    public void delete(int id) {
        categoryDbStore.delete(id);
    }
}
