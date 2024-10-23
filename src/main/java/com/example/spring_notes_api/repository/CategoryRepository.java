package com.example.spring_notes_api.repository;

import com.example.spring_notes_api.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer>  {
}
