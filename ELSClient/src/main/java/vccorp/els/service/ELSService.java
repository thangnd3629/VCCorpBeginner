package vccorp.els.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vccorp.els.entity.Recipe;
import vccorp.els.repository.RecipeRepository;

import java.util.List;


@Service
public class ELSService {
    @Autowired
    private RecipeRepository recipeRepo;



    public void getALl() {
        List<Recipe> recipes = recipeRepo.getAllRecipeCustom();
    }
    public void incrementPrepTime(String name, int incs){
        List<Recipe> desiredRecipes = recipeRepo.getAllByTitle(name);
        if (desiredRecipes != null){
            for (Recipe recipe : desiredRecipes){
                recipeRepo.updatePrepTime(recipe.getId(), recipe.getPreparation_time_minutes()+incs);
            }
        }
    }


}
