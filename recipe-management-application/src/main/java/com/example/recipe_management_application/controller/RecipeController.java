package com.example.recipe_management_application.controller;

import com.example.recipe_management_application.model.Recipe;
import com.example.recipe_management_application.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public String listRecipes(Model model) {
        model.addAttribute("recipes", recipeService.getAllRecipes());
        return "recipes/list";
    }

    @GetMapping("/category")
    public String searchByCategory(@RequestParam String category, Model model) {

        List<Recipe> recipes = recipeService.getRecipesByCategory(category);
        model.addAttribute("recipes", recipes);

        // Check if there are no recipes found
        if (recipes.isEmpty()) {
            model.addAttribute("noRecipesMessage", "There are no recipes found for this category.");
        }
        return "recipes/view-all";
    }


    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("recipe", new Recipe());
        return "recipes/form";
    }

    @PostMapping
    public String saveRecipe(@ModelAttribute Recipe recipe) {
        recipeService.saveRecipe(recipe);
        return "redirect:/recipes";
    }

    @PostMapping("/add")
    public String addRecipe(@RequestParam String name,
                            @RequestParam String category,
                            @RequestParam String ingredients,
                            @RequestParam String instructions,
                            @RequestParam int preparationTime)  {
        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setCategory(category);
        recipe.setIngredients(ingredients);
        recipe.setInstructions(instructions);
        recipe.setPreparationTime(preparationTime);

        recipeService.saveRecipe(recipe);

        return "redirect:/recipes?message=success";
    }

    @PostMapping("/{id}/edit")
    public String editRecipe(@PathVariable("id") Long id,
                             @RequestParam String name,
                             @RequestParam String category,
                             @RequestParam String ingredients,
                             @RequestParam String instructions,
                             @RequestParam int preparationTime) {
        Recipe recipe = recipeService.findById(id);
        recipe.setName(name);
        recipe.setCategory(category);
        recipe.setIngredients(ingredients);
        recipe.setInstructions(instructions);
        recipe.setPreparationTime(preparationTime);

        recipeService.saveRecipe(recipe);

        return "redirect:/recipes/view-all";
    }

    @GetMapping("/view-all")
    public String viewAllRecipes(Model model) {
        // Fetch all recipes from the service
        List<Recipe> recipes = recipeService.getAllRecipes();
        model.addAttribute("recipes", recipes);

        return "recipes/view-all";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Recipe recipe = recipeService.findById(id);
        model.addAttribute("recipe", recipe);

        return "recipes/edit";
    }

    @PostMapping("/{id}/delete")
    public String deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return "redirect:/recipes/view-all";
    }
}