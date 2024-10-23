package com.example.spring_notes_api.service;

import com.example.spring_notes_api.model.Category;
import com.example.spring_notes_api.model.CategoryRequest;
import com.example.spring_notes_api.model.Response;

import java.util.List;

public interface CategoryService {
    Response<List<Category>> getAll();
    Response<Category> create(CategoryRequest request);
}
