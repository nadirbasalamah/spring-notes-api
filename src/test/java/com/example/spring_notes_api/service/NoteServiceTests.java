package com.example.spring_notes_api.service;

import com.example.spring_notes_api.model.Category;
import com.example.spring_notes_api.model.Note;
import com.example.spring_notes_api.model.NoteRequest;
import com.example.spring_notes_api.model.Response;
import com.example.spring_notes_api.repository.CategoryRepository;
import com.example.spring_notes_api.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class NoteServiceTests {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private CategoryRepository categoryRepository;

    private NoteService noteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        noteService = new NoteServiceImpl(noteRepository, categoryRepository);
    }

    @Test
    void testGetAllNotes() {
        List<Note> notes = new ArrayList<>();

        notes.add(new Note(1,new Category(1,"category"),"title","desc"));

        when(noteRepository.findAll()).thenReturn(notes);

        Response<List<Note>> response = noteService.getAll();

        assertEquals("all notes", response.getMessage());
        assertEquals(1, response.getData().size());
    }

    @Test
    void testGetNoteByIdFound() {
        Note note = new Note(1,new Category(1,"category"),"title","desc");

        when(noteRepository.findById(1)).thenReturn(Optional.of(note));

        Response<Note> response = noteService.getById(1);

        assertEquals("note found", response.getMessage());
        assertEquals(note, response.getData());
    }

    @Test
    void testGetNoteByIdNotFound() {
        when(noteRepository.findById(1)).thenReturn(Optional.empty());

        Response<Note> response = noteService.getById(1);

        assertEquals("note not found", response.getMessage());
        assertNull(response.getData());
    }

    @Test
    void testCreateNote() {
        NoteRequest request = new NoteRequest("title","description",1);
        Note createdNote = new Note(1,new Category(1,"category"),"title","description");

        when(categoryRepository.findById(1)).thenReturn(Optional.of(new Category(1, "category")));
        when(noteRepository.save(any(Note.class))).thenReturn(createdNote);

        Response<Note> response = noteService.create(request);

        assertEquals("note created", response.getMessage());
        assertEquals(createdNote, response.getData());
    }

    @Test
    void testUpdateNoteFound() {
        NoteRequest request = new NoteRequest("updated title","updated description",1);
        Note existingNote = new Note(1,new Category(1,"category"),"updated title","updated description");

        when(categoryRepository.findById(1)).thenReturn(Optional.of(new Category(1, "category")));
        when(noteRepository.findById(1)).thenReturn(Optional.of(existingNote));
        when(noteRepository.save(any(Note.class))).thenReturn(existingNote);

        Response<Note> response = noteService.update(request, 1);

        assertEquals("note updated", response.getMessage());
        assertEquals(existingNote, response.getData());
        assertEquals("updated title",existingNote.getTitle());
        assertEquals("updated description",existingNote.getDescription());
        assertEquals(1,existingNote.getCategory().getId());
    }

    @Test
    void testUpdateNoteNotFound() {
        NoteRequest request = new NoteRequest("updated title","updated description",1);

        when(categoryRepository.findById(1)).thenReturn(Optional.of(new Category(1, "category")));
        when(noteRepository.findById(1)).thenReturn(Optional.empty());

        Response<Note> response = noteService.update(request, 1);

        assertEquals("note not found", response.getMessage());
        assertNull(response.getData());
    }

    @Test
    void testDeleteNoteFound() {
        when(noteRepository.findById(1)).thenReturn(Optional.of(new Note()));

        boolean result = noteService.delete(1);

        assertTrue(result);
        verify(noteRepository, times(1)).delete(any(Note.class));
    }

    @Test
    void testDeleteNoteNotFound() {
        when(noteRepository.findById(1)).thenReturn(Optional.empty());

        boolean result = noteService.delete(1);

        assertFalse(result);
        verify(noteRepository, never()).delete(any(Note.class));
    }

}
