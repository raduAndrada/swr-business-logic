package ro.swr.dishes.mappers;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import model.Dish;
import model.NutritionalValue;
import model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ro.swr.dishes.repository.entities.DishEntity;
import ro.swr.dishes.repository.entities.RecipeEntity;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ro.swr.dishes.TestUtils.*;

class ModelEntityMapperTest {

    private ModelEntityMapper<Recipe, RecipeEntity> recipeModelMapper;
    private ModelEntityMapper<Dish, DishEntity> dishModelMapper;

    private static Gson gson = new Gson();

    @BeforeEach
    void setup() {
        recipeModelMapper =
                new ModelEntityMapper(Recipe.class, RecipeEntity.class);
        dishModelMapper =
                new ModelEntityMapper(Dish.class, DishEntity.class);
    }


    @ParameterizedTest
    @MethodSource({"provideRecipeValidFromAndToEntityTestData", "provideRecipeValidFromEntityTestData"})
    void fromEntity(RecipeEntity entity, Recipe model) {

        Recipe actual = recipeModelMapper.fromEntity(entity);
        assertEquals(model, actual);
    }

    @ParameterizedTest
    @MethodSource("provideRecipeValidFromAndToEntityTestData")
    void toEntity(RecipeEntity entity, Recipe model) {
        RecipeEntity actual = recipeModelMapper.toEntity(model);
        trimEntityWhiteSpaces(actual);
        trimEntityWhiteSpaces(entity);
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(entity);
    }

    @ParameterizedTest
    @MethodSource("provideDishValidFromAndToEntityTestData")
    void dishFromEntity(DishEntity entity, Dish model) {

        Dish actual = dishModelMapper.fromEntity(entity);
        assertEquals(model, actual);
    }

    @ParameterizedTest
    @MethodSource("provideDishValidFromAndToEntityTestData")
    void dishToEntity(DishEntity entity, Dish model) {
        DishEntity actual = dishModelMapper.toEntity(model);
        trimEntityWhiteSpaces(actual);
        trimEntityWhiteSpaces(entity);
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(entity);
    }

    private static Stream<Arguments> provideRecipeValidFromAndToEntityTestData() {
        return Stream.of(
                Arguments.of(
                        getTestRecipeEntityFromFile(TEST_RECIPE_NAME, SINGLE_INGREDIENT, NUTRITIONAL_VALUE_FULL),
                        getTestRecipe(
                                TEST_RECIPE_NAME,
                                gson.fromJson(readJsonAsString(SINGLE_INGREDIENT), List.class),
                                gson.fromJson(readJsonAsString(NUTRITIONAL_VALUE_FULL), NutritionalValue.class)
                        )
                ),
                Arguments.of(
                        getTestRecipeEntityFromFile(TEST_RECIPE_NAME, MULTIPLE_INGREDIENTS, NUTRITIONAL_VALUE_PARTIAL),
                        getTestRecipe(
                                TEST_RECIPE_NAME,
                                gson.fromJson(readJsonAsString(MULTIPLE_INGREDIENTS), List.class),
                                gson.fromJson(readJsonAsString(NUTRITIONAL_VALUE_PARTIAL), NutritionalValue.class)

                        )
                ),
                Arguments.of(
                        getTestRecipeEntity(TEST_RECIPE_NAME, null, null),
                        getTestRecipe(TEST_RECIPE_NAME, null, null)
                )
        );
    }


    private static Stream<Arguments> provideRecipeValidFromEntityTestData() {
        return Stream.of(
                Arguments.of(
                        getTestRecipeEntity(TEST_RECIPE_NAME, "[]", "   "),
                        getTestRecipe(TEST_RECIPE_NAME, Lists.newArrayList(), null)
                )
        );
    }

    private static Stream<Arguments> provideDishValidFromAndToEntityTestData() {
        return Stream.of(
                Arguments.of(
                        getTestDishEntity(
                                TEST_RECIPE_NAME,
                                SINGLE_INGREDIENT,
                                NUTRITIONAL_VALUE_FULL),
                        getTestDish(
                                TEST_RECIPE_NAME,
                                SINGLE_INGREDIENT,
                                NUTRITIONAL_VALUE_FULL
                        )
                ),
                Arguments.of(
                        getTestDishEntity(
                                TEST_RECIPE_NAME,
                                MULTIPLE_INGREDIENTS,
                                NUTRITIONAL_VALUE_PARTIAL),
                        getTestDish(
                                TEST_RECIPE_NAME,
                                MULTIPLE_INGREDIENTS,
                                NUTRITIONAL_VALUE_PARTIAL
                        )
                )
        );
    }


}