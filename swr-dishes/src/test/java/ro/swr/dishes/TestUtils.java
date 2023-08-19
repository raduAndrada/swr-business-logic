package ro.swr.dishes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.*;
import model.rest.SearchRequest;
import org.assertj.core.util.Lists;
import org.springframework.util.StringUtils;
import ro.swr.dishes.repository.entities.DishEntity;
import ro.swr.dishes.repository.entities.IngredientEntity;
import ro.swr.dishes.repository.entities.RecipeEntity;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;

public class TestUtils {

    public static String JSON_FOLDER_PATH = "src/test/resources/json/";
    public static String SINGLE_INGREDIENT = JSON_FOLDER_PATH + "single-ingredient.json";
    public static String MULTIPLE_INGREDIENTS = JSON_FOLDER_PATH + "multiple-ingredients.json";
    public static String NUTRITIONAL_VALUE_PARTIAL = JSON_FOLDER_PATH + "nutritional-value-partial.json";
    public static String NUTRITIONAL_VALUE_FULL = JSON_FOLDER_PATH + "nutritional-value-full.json";
    public static String MULTIPLE_DISHES = JSON_FOLDER_PATH + "multiple-dishes.json";

    public static String TEST_RECIPE_NAME = "test";
    public static final Instant TEST_INSTANT = Instant.parse("2023-08-15T18:35:24.00Z");

    private static final Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    public static String readJsonAsString(String fileName) {
        try {
            return new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (Exception e) {
            return null;
        }
    }

    public static RecipeEntity getTestRecipeEntityFromFile(
            String name,
            String ingredientsFileName,
            String nutritionalValueFileName
    ) {
        return getTestRecipeEntity(
                name,
                readJsonAsString(ingredientsFileName),
                readJsonAsString(nutritionalValueFileName));
    }

    public static DishEntity getTestDishEntity(String name, String ingredientsFileName, String nutritionalValueFileName) {
        return getTestDishEntity(name, null, null, null, ingredientsFileName, nutritionalValueFileName);
    }

    public static DishEntity getTestDishEntity(
            String name,
            BigDecimal price,
            String labels,
            Category category,
            String ingredientsFileName,
            String nutritionalValueFileName) {
        return DishEntity.builder()
                .name(name)
                .price(price)
                .labels(labels)
                .category(category)
                .ingredients(
                        gson
                                .fromJson(
                                        readJsonAsString(ingredientsFileName),
                                        new TypeToken<List<IngredientEntity>>() {
                                        }.getType()
                                )
                )
                .nutritionalValue(readJsonAsString(nutritionalValueFileName))
                .build();
    }

    public static Dish getTestDish(String name, String ingredientsFileName, String nutritionalValueFileName) {
        return Dish.builder()
                .name(name)
                .ingredients(
                        gson.fromJson(
                                readJsonAsString(ingredientsFileName),
                                new TypeToken<List<Ingredient>>() {
                                }.getType()
                        )
                )
                .nutritionalValue(gson.fromJson(readJsonAsString(nutritionalValueFileName), NutritionalValue.class))
                .labels(Lists.newArrayList())
                .build();
    }

    public static <T> List<T> getTestListFromJson(String jsonFilename, Type type) {
        return gson.fromJson(
                readJsonAsString(jsonFilename),
                type
        );
    }

    public static RecipeEntity getTestRecipeEntity(
            String name,
            String ingredients,
            String nutritionalValue
    ) {
        return RecipeEntity.builder()
                .name(name)
                .ingredients(ingredients)
                .nutritionalValue(nutritionalValue)
                .build();
    }

    public static Recipe getTestRecipe(
            String name,
            List<Ingredient> ingredients,
            NutritionalValue nutritionalValue
    ) {
        return Recipe.builder()
                .name(name)
                .ingredients(ingredients)
                .nutritionalValue(nutritionalValue)
                .build();
    }


    public static <T> SearchRequest<T> buildSearchRequest(T filter) {
        return (SearchRequest<T>) SearchRequest.builder()
                .filterOptions(filter)
                .offset(0)
                .size(10)
                .build();
    }

    public static void trimEntityWhiteSpaces(RecipeEntity entity) {
        entity.setIngredients(trimWhiteSpaces(entity.getIngredients()));
        entity.setNutritionalValue(trimWhiteSpaces(entity.getNutritionalValue()));
    }

    public static void trimEntityWhiteSpaces(DishEntity entity) {
        entity.setNutritionalValue(trimWhiteSpaces(entity.getNutritionalValue()));
    }

    public static String trimWhiteSpaces(String field) {
        return StringUtils.trimAllWhitespace(field);
    }

}
