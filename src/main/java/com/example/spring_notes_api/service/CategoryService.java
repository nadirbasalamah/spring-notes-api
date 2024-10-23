package com.example.spring_notes_api.service;

import com.example.spring_notes_api.model.*;
import com.example.spring_notes_api.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;

    public Response<List<Category>> getAll() {
        List<Category> categories = repository.findAll();

        return new Response<>(
                "all categories",
                categories
        );
    }

    public Response<Category> create(CategoryRequest request) {
        Category categoryData = Category.builder()
                .name(request.getName())
                .build();

        Category category = repository.save(categoryData);

        return new Response<>(
                "category created",
                category
        );
    }
}
