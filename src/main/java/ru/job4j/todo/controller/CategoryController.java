package ru.job4j.todo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.service.CategoryService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/all")
    public String create(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "category/all";
    }

    @GetMapping("/createpage")
    public String createPage(@ModelAttribute("category") Category category) {
        return "category/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("task") Category category) {
        categoryService.create(category);
        return "redirect:/";
    }

    @GetMapping("/id/{id}")
    public String findIdCategory(Model model, @PathVariable("id") int id) {
        model.addAttribute("category", categoryService.findById(id));
        return "category/id";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        categoryService.delete(id);
        return "redirect:/index";
    }
}
