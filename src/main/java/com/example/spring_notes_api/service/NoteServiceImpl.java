package com.example.spring_notes_api.service;

import com.example.spring_notes_api.model.Category;
import com.example.spring_notes_api.model.Note;
import com.example.spring_notes_api.model.NoteRequest;
import com.example.spring_notes_api.model.Response;
import com.example.spring_notes_api.repository.CategoryRepository;
import com.example.spring_notes_api.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final CategoryRepository categoryRepository;

    public Response<List<Note>> getAll() {
        List<Note> notes = noteRepository.findAll();

        return new Response<>(
                "all notes",
                notes
        );
    }

    public Response<Note> getById(Integer id) {
        Optional<Note> noteData = noteRepository.findById(id);

        return noteData.map(note -> new Response<>(
                "note found",
                note
        )).orElseGet(() -> new Response<>(
                "note not found",
                null
        ));
    }

    public Response<Note> create(NoteRequest request) {
        Optional<Category> categoryData = categoryRepository.findById(request.getCategoryId());

        if (categoryData.isEmpty()) {
            return new Response<>(
                    "create note failed, category not found",
                    null
            );
        }

        Note noteData = Note.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .category(categoryData.get())
                .build();

        Note note = noteRepository.save(noteData);

        return new Response<>(
                "note created",
                note
        );
    }

    public Response<Note> update(NoteRequest request, Integer id) {
        Optional<Category> categoryData = categoryRepository.findById(request.getCategoryId());

        if (categoryData.isEmpty()) {
            return new Response<>(
                    "update note failed, category not found",
                    null
            );
        }

        Optional<Note> noteData = noteRepository.findById(id);

        if (noteData.isEmpty()) {
            return new Response<>(
                    "note not found",
                    null
            );
        }

        Note updatedNote = noteData.get();

        updatedNote.setTitle(request.getTitle());
        updatedNote.setDescription(request.getDescription());
        updatedNote.setCategory(categoryData.get());

        Note note = noteRepository.save(updatedNote);

        return new Response<>(
                "note updated",
                note
        );
    }

    public boolean delete(Integer id) {
        Optional<Note> noteData = noteRepository.findById(id);

        if (noteData.isEmpty()) {
            return false;
        }

        noteRepository.delete(noteData.get());

        return true;
    }
}
