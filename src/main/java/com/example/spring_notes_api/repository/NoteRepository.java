package com.example.spring_notes_api.repository;

import com.example.spring_notes_api.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Integer> {

}
