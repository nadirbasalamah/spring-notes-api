package com.example.spring_notes_api.controller;

import com.example.spring_notes_api.model.*;
import com.example.spring_notes_api.service.CategoryService;
import com.example.spring_notes_api.utils.Utils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService service;

    @GetMapping
    public ResponseEntity<Response<List<Category>>> getAll() {
        Response<List<Category>> response = service.getAll();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<Category>> create(@Valid @RequestBody CategoryRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String validationErrors = Utils.getValidationErrorMessages(bindingResult);

            return new ResponseEntity<>(new Response<>(validationErrors, null), HttpStatus.BAD_REQUEST);
        }

        Response<Category> response = service.create(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
