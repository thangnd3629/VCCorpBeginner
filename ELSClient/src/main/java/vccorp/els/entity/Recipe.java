package vccorp.els.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


import java.util.Date;
import java.util.List;

@Document(indexName = "recipe")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class Recipe {

    @NoArgsConstructor
    public static class Serving{

        public int min;
        public int max;
    }
    @NoArgsConstructor
    public static class Ingredient{
        public String name;
        public String quantity;
    }
    @Id
    public int id;
    public String title;
    public String description;
    @Field(name = "preparation_time_minutes")
    public int preparation_time_minutes;
    public Serving servings;
    public List<String> steps;
    @Field(type = FieldType.Nested)
    public List<Ingredient> ingredients;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    public Date created;
    public List<String> ratings;


}

