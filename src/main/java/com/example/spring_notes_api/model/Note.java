package com.example.spring_notes_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue
    private Integer id;

    private String title;
    private String description;
}
