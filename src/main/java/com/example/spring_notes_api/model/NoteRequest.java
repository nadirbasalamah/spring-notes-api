package com.example.spring_notes_api.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteRequest {
    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "description is required")
    private String description;
}
