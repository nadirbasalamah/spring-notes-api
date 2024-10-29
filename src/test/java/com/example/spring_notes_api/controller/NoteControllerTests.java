package com.example.spring_notes_api.controller;

import com.example.spring_notes_api.model.Category;
import com.example.spring_notes_api.model.Note;
import com.example.spring_notes_api.model.NoteRequest;
import com.example.spring_notes_api.model.Response;
import com.example.spring_notes_api.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NoteControllerTests {

    @Mock
    private NoteService service;

    private NoteController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new NoteController(service);
    }

    @Test
    void testGetAllNotes() {
        List<Note> notes = new ArrayList<>();

        notes.add(new Note(1,new Category(1,"category"),"title","desc"));

        when(service.getAll()).thenReturn(new Response<>("all notes", notes));

        ResponseEntity<Response<List<Note>>> responseEntity = controller.getAll();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("all notes", responseEntity.getBody().getMessage());
        assertEquals(1, responseEntity.getBody().getData().size());
    }

    @Test
    void testGetNoteByIdFound() {
        Note note = new Note(1,new Category(1,"category"),"title","desc");

        when(service.getById(1)).thenReturn(new Response<>("note found", note));

        ResponseEntity<Response<Note>> responseEntity = controller.getById(1);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("note found", responseEntity.getBody().getMessage());
        assertEquals(note, responseEntity.getBody().getData());
    }

    @Test
    void testGetNoteByIdNotFound() {
        when(service.getById(1)).thenReturn(new Response<>("note not found", null));

        ResponseEntity<Response<Note>> responseEntity = controller.getById(1);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("note not found", responseEntity.getBody().getMessage());
    }

    @Test
    void testCreateNote() {
        NoteRequest request = new NoteRequest("title","description",1);
        Note createdNote = new Note(1,new Category(1,"category"),"title","description");

        when(service.create(request)).thenReturn(new Response<>("note created", createdNote));

        BindingResult bindingResult = mock(BindingResult.class);

        ResponseEntity<Response<Note>> responseEntity = controller.create(request, bindingResult);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("note created", responseEntity.getBody().getMessage());
        assertEquals(createdNote, responseEntity.getBody().getData());
    }


    @Test
    void testUpdateBookFound() {
        NoteRequest request = new NoteRequest("updated title","updated description",1);
        Note existingNote = new Note(1,new Category(1,"category"),"updated title","updated description");

        when(service.update(request, 1)).thenReturn(new Response<>("note updated", existingNote));

        BindingResult bindingResult = mock(BindingResult.class);

        ResponseEntity<Response<Note>> responseEntity = controller.update(1, request, bindingResult);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("note updated", responseEntity.getBody().getMessage());
        assertEquals(existingNote, responseEntity.getBody().getData());
    }

    @Test
    void testUpdateBookNotFound() {
        NoteRequest request = new NoteRequest("updated title","updated description",1);

        when(service.update(request, 1)).thenReturn(new Response<>("note not found", null));

        BindingResult bindingResult = mock(BindingResult.class);

        ResponseEntity<Response<Note>> responseEntity = controller.update(1, request, bindingResult);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("note not found", responseEntity.getBody().getMessage());
    }

    @Test
    void testDeleteBookFound() {
        when(service.delete(1)).thenReturn(true);

        ResponseEntity<Response<Object>> responseEntity = controller.delete(1);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("note deleted", responseEntity.getBody().getMessage());
    }

    @Test
    void testDeleteBookNotFound() {
        when(service.delete(1)).thenReturn(false);

        ResponseEntity<Response<Object>> responseEntity = controller.delete(1);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("note not found", responseEntity.getBody().getMessage());
    }
}
