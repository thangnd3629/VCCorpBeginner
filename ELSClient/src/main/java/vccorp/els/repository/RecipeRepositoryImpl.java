package vccorp.els.repository;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vccorp.els.entity.Recipe;
import vccorp.els.repository.mapper.RecipeMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional(readOnly = true)
public class RecipeRepositoryImpl implements RecipeRepositoryCustom {
    @Autowired
    public RestHighLevelClient client;


    @Override
    public List<Recipe> getAllRecipeCustom() {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("recipe");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(sourceBuilder);
        SearchResponse response;
        try {
            response = client.search(searchRequest, RequestOptions.DEFAULT);
            return RecipeMapper.deserializeResponse(response);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Recipe> getAllByTitle(String title) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("recipe");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("title", title);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(matchQueryBuilder);
        sourceBuilder.query(
                boolQueryBuilder
        );
        searchRequest.source(sourceBuilder);
        SearchResponse response;
        try {
            response = client.search(searchRequest, RequestOptions.DEFAULT);
            return RecipeMapper.deserializeResponse(response);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updatePrepTime(int id, int amount) {

        Map<String, Object> jsonMap = new HashMap<>();

        jsonMap.put("preparation_time_minutes", amount);
        UpdateRequest request = new UpdateRequest(
                "recipe",
                "" + id).doc(jsonMap);

        try {
            client.update(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
