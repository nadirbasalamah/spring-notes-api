package com.example.spring_notes_api.service;

import com.example.spring_notes_api.model.Note;
import com.example.spring_notes_api.model.NoteRequest;
import com.example.spring_notes_api.model.Response;
import com.example.spring_notes_api.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository repository;

    public Response<List<Note>> getAll() {
        List<Note> notes = repository.findAll();

        return new Response<>(
                "all notes",
                notes
        );
    }

    public Response<Note> getById(Integer id) {
        Optional<Note> noteData = repository.findById(id);

        return noteData.map(note -> new Response<>(
                "note found",
                note
        )).orElseGet(() -> new Response<>(
                "note not found",
                null
        ));
    }

    public Response<Note> create(NoteRequest request) {
        Note noteData = Note.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .build();

        Note note = repository.save(noteData);

        return new Response<>(
                "note created",
                note
        );
    }

    public Response<Note> update(NoteRequest request, Integer id) {
        Optional<Note> noteData = repository.findById(id);

        if (noteData.isEmpty()) {
            return new Response<>(
                    "note not found",
                    null
            );
        }

        Note updatedNote = noteData.get();

        updatedNote.setTitle(request.getTitle());
        updatedNote.setDescription(request.getDescription());

        Note note = repository.save(updatedNote);

        return new Response<>(
                "note updated",
                note
        );
    }

    public boolean delete(Integer id) {
        Optional<Note> noteData = repository.findById(id);

        if (noteData.isEmpty()) {
            return false;
        }

        repository.delete(noteData.get());

        return true;
    }

}
