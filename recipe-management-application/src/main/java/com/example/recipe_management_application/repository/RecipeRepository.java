package com.example.recipe_management_application.repository;

import com.example.recipe_management_application.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByCategory(String category);
}
