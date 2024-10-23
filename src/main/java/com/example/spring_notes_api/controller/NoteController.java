package com.example.spring_notes_api.controller;

import com.example.spring_notes_api.model.Note;
import com.example.spring_notes_api.model.NoteRequest;
import com.example.spring_notes_api.model.Response;
import com.example.spring_notes_api.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService service;

    @GetMapping
    public ResponseEntity<Response<List<Note>>> getAll() {
        Response<List<Note>> response = service.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<Note>> getById(@PathVariable("id") Integer id) {
        Response<Note> response = service.getById(id);

        if (response.getData() == null) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<Note>> create(@Valid @RequestBody NoteRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            String validationErrors = fieldErrors.getFirst().getDefaultMessage();

            if (fieldErrors.size() > 1) {
                for (int i = 1; i < fieldErrors.size(); i++) {
                    validationErrors += ", " + fieldErrors.get(i).getDefaultMessage();
                }
            }

            return new ResponseEntity<>(new Response<>(validationErrors, null), HttpStatus.BAD_REQUEST);
        }

        Response<Note> response = service.create(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Note>> update(@PathVariable("id") Integer id, @Valid @RequestBody NoteRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new Response<>(bindingResult.getFieldErrors().toString(), null), HttpStatus.BAD_REQUEST);
        }

        Response<Note> response = service.update(request, id);

        if (response.getData() == null) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Object>> delete(@PathVariable("id") Integer id) {
        boolean isDeleted = service.delete(id);

        if (!isDeleted) {
            Response<Object> response = new Response<>("note not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(new Response<>("note deleted", null));
    }
}
