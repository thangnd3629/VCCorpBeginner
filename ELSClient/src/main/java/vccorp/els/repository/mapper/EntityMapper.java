package vccorp.els.repository.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import vccorp.els.entity.BaseEntity;
import vccorp.els.entity.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeMapper {
    public static <T extends BaseEntity> List<T> deserializeResponse(SearchResponse response, Class<T> clazz) {

            SearchHit[] hits = response.getHits().getHits();
            ObjectMapper mapper = new ObjectMapper();
            List<T> recipes = new ArrayList<>();
            try
            {
                for (SearchHit hit : hits) {
                    T recipe = mapper.readValue(hit.getSourceAsString(), clazz);
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
