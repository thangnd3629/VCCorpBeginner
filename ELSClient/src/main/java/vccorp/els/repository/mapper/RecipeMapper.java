package vccorp.els.repository.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import vccorp.els.entity.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeMapper {
    public static List<Recipe> deserializeResponse(SearchResponse response) {

            SearchHit[] hits = response.getHits().getHits();
            ObjectMapper mapper = new ObjectMapper();
            List<Recipe> recipes = new ArrayList<>();
            try
            {
                for (SearchHit hit : hits) {
                    Recipe recipe = mapper.readValue(hit.getSourceAsString(), Recipe.class);
                    recipe.setId(Integer.parseInt(hit.getId()));
                    recipes.add(recipe);

                }
                return recipes;
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;



    }
}
