package com.example.spring_notes_api.service;

import com.example.spring_notes_api.model.Note;
import com.example.spring_notes_api.model.NoteRequest;
import com.example.spring_notes_api.model.Response;

import java.util.List;

public interface NoteService {
    Response<List<Note>> getAll();
    Response<Note> getById(Integer id);
    Response<Note> create(NoteRequest request);
    Response<Note> update(NoteRequest request, Integer id);
    boolean delete(Integer id);
}
