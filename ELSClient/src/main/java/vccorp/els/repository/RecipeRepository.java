package vccorp.els.repository;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import org.springframework.stereotype.Repository;
import vccorp.els.entity.Recipe;

import java.util.List;

@Repository
public interface RecipeRepository extends ElasticsearchRepository<Recipe, Integer> , RecipeRepositoryCustom {
    @Query(
           "{\"bool\": {\n" +
                   "      \n" +
                   "      \"must\": [\n" +
                   "        {\n" +
                   "          \"match\": {\n" +
                   "            \"ingredients.name.keyword\": \"Extra-virgin olive oil\"\n" +
                   "          }\n" +
                   "          \n" +
                   "        },\n" +
                   "        {\n" +
                   "          \"match\": {\n" +
                   "            \"ingredients.quantity.keyword\": \"2 cups\"\n" +
                   "          }\n" +
                   "        }\n" +
                   "      ]\n" +
                   "      \n" +
                   "    }}"
    )
    List<Recipe> getAllRecipe();

}
