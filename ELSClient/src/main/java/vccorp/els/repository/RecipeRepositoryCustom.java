package vccorp.els.repository;

import vccorp.els.entity.Recipe;

import java.util.List;


public interface RecipeRepositoryCustom {
    List<Recipe> getAllRecipeCustom();
    List<Recipe> getAllByTitle(String title);
    void updatePrepTime(int id, int amount);

}
