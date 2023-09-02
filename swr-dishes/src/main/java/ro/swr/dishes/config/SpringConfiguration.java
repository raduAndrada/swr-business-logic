package ro.swr.dishes.config;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import model.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.swr.dishes.repository.CategoryRepository;
import ro.swr.dishes.repository.DishRepository;
import ro.swr.dishes.repository.RecipeRepository;
import ro.swr.dishes.repository.SubcategoryRepository;
import ro.swr.dishes.repository.entities.CategoryEntity;
import ro.swr.dishes.repository.entities.DishEntity;
import ro.swr.dishes.repository.entities.RecipeEntity;
import ro.swr.dishes.repository.entities.SubcategoryEntity;
import ro.swr.services.SwrServiceBase;
import ro.swr.services.SwrServiceBaseImpl;
import ro.swr.services.mappers.ModelEntityMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Configuration
public class SpringConfiguration {


    @Bean
    public SwrServiceBase recipeService(RecipeRepository recipeRepository) {
        return new SwrServiceBaseImpl(recipeRepository, new ModelEntityMapper(Recipe.class, RecipeEntity.class));
    }

    @Bean
    public CommandLineRunner dataLoader(DishRepository dishRepository,
                                        CategoryRepository categoryRepository,
                                        SubcategoryRepository subcategoryRepository) {

        Gson gson = new Gson();

        CategoryEntity sandwiches = CategoryEntity.builder()
                .name("Sandwiches")
                .icon("fa-bread-slice")

                .build();
        categoryRepository.save(sandwiches);

        CategoryEntity togo = CategoryEntity.builder()
                .name("ToGo")
                .icon("fa-person-running")
                .build();

        categoryRepository.save(togo);
        CategoryEntity brunch = CategoryEntity.builder()
                .name("Brunch")
                .icon("fa-bacon")

                .build();
        categoryRepository.save(brunch);
        CategoryEntity drinks = CategoryEntity.builder()
                .name("Drinks")
                .icon("fa-martini-glass")
                .build();
        categoryRepository.save(drinks);


        SubcategoryEntity brunchSub = SubcategoryEntity.builder()
                .name("Eggs&Bacon")
                .icon("fa-bacon")
                .category(brunch)
                .build();
        subcategoryRepository.save(brunchSub);
        SubcategoryEntity drinksSub1 = SubcategoryEntity.builder()
                .name("Hot")
                .icon("fa-martini-glass")
                .category(drinks)
             .build();
        SubcategoryEntity drinksSub2 = SubcategoryEntity.builder()
                .name("Cold")
                .icon("fa-martini-glass")
                .category(drinks)
                .build();
        SubcategoryEntity drinksSub3 = SubcategoryEntity.builder()
                .name("Cocktails")
                .icon("fa-martini-glass")
                .category(drinks)
                .build();
        SubcategoryEntity drinksSub4 = SubcategoryEntity.builder()
                .name("Wines")
                .icon("fa-martini-glass")
                .category(drinks)
                .build();
        subcategoryRepository.save(drinksSub1);
        subcategoryRepository.save(drinksSub2);
        subcategoryRepository.save(drinksSub3);
        subcategoryRepository.save(drinksSub4);

        SubcategoryEntity sandwichesSub = SubcategoryEntity.builder()
                .name("All Sandwiches")
                .icon("fa-bread-slice")
                .category(sandwiches)
                .build();
        subcategoryRepository.save(sandwichesSub);

        SubcategoryEntity togoSub = SubcategoryEntity.builder()
                .name("Hot")
                .icon("fa-person-running")
                .category(togo)
                .build();

        subcategoryRepository.save(togoSub);




        Ingredient ingredient1 = Ingredient.builder()
                .shortName("ingredient01")
                .longName("ingredient01")
                .description("description01")
                .quantity(100.0)
                .um("g")
                .build();
        Ingredient ingredient2 = Ingredient.builder()
                .shortName("ingredient02")
                .longName("ingredient02")
                .description("description02")
                .quantity(100.0)
                .um("g")
                .build();
        Ingredient ingredient3 = Ingredient.builder()
                .shortName("ingredient03")
                .longName("ingredient03")
                .description("description03")
                .quantity(1.0)
                .um("piece")
                .build();

        Ingredient ingredient4 = Ingredient.builder()
                .shortName("ingredient01")
                .longName("ingredient01")
                .description("description01")
                .quantity(100.0)
                .um("g")
                .build();
        Ingredient ingredient5 = Ingredient.builder()
                .shortName("ingredient02")
                .longName("ingredient02")
                .description("description02")
                .quantity(100.0)
                .um("g")
                .build();
        Ingredient ingredient6 = Ingredient.builder()
                .shortName("ingredient03")
                .longName("ingredient03")
                .description("description03")
                .quantity(1.0)
                .um("piece")
                .build();

        NutritionalValue nv = NutritionalValue.builder()
                .ingredients(Map.of("ingredient01", 40.0, "ingredient02", 40.0, "ingredient03", 20.0))
                .values(Map.of("carbs", 36.0, "fiber", 7.0, "fats", 8.0, "saturated", 1.0))
                .kcal(157.0)
                .kj(1132.0)
                .build();

        DishEntity dish1 = DishEntity.builder()
                .name("dish01")
                .price(BigDecimal.TEN)
                .labels(gson.toJson(ImmutableList.of(Label.SUGAR_FREE, Label.GLUTEN_FREE)))
                .subcategory(sandwichesSub)
                .ingredients(
                        gson.toJson(List.of(ingredient1, ingredient2, ingredient3, ingredient4))
                )
                .nutritionalValue(gson.toJson(nv))
                .build();


        DishEntity dish2 = DishEntity.builder()
                .name("dish02")
                .price(BigDecimal.valueOf(25))
                .labels(gson.toJson(ImmutableList.of(Label.FISH)))
                .subcategory(togoSub)
                .ingredients(
                        gson.toJson(List.of(ingredient1, ingredient4, ingredient5))
                )
                .build();


        DishEntity dish3 = DishEntity.builder()
                .name("dish03")
                .price(BigDecimal.valueOf(40))
                .labels(gson.toJson(ImmutableList.of(Label.VEGAN)))
                .subcategory(sandwichesSub)
                .ingredients(
                        gson.toJson(List.of(ingredient1, ingredient2, ingredient3, ingredient4, ingredient5, ingredient6))
                )
                .build();

        DishEntity dish4 = DishEntity.builder()
                .name("dish03")
                .price(BigDecimal.valueOf(40))
                .labels(gson.toJson(ImmutableList.of(Label.VEGAN)))
                .subcategory(brunchSub)
                .ingredients(
                        gson.toJson(List.of(ingredient1, ingredient2, ingredient3, ingredient4, ingredient5, ingredient6))
                )
                .build();

        DishEntity dish5 = DishEntity.builder()
                .name("dish03")
                .price(BigDecimal.valueOf(40))
                .labels(gson.toJson(ImmutableList.of(Label.VEGAN)))
                .subcategory(drinksSub1)
                .build();

        DishEntity dish11 = DishEntity.builder()
                .name("dish03")
                .price(BigDecimal.valueOf(40))
                .labels(gson.toJson(ImmutableList.of(Label.VEGAN)))
                .subcategory(drinksSub1)
                .build();
        DishEntity dish10 = DishEntity.builder()
                .name("dish03")
                .price(BigDecimal.valueOf(40))
                .labels(gson.toJson(ImmutableList.of(Label.VEGAN)))
                .subcategory(drinksSub1)
                .build();
        DishEntity dish6 = DishEntity.builder()
                .name("dish03")
                .price(BigDecimal.valueOf(40))
                .labels(gson.toJson(ImmutableList.of(Label.VEGAN)))
                .subcategory(drinksSub1)
                .build();
        DishEntity dish7 = DishEntity.builder()
                .name("dish03")
                .price(BigDecimal.valueOf(40))
                .labels(gson.toJson(ImmutableList.of(Label.VEGAN)))
                .subcategory(drinksSub2)
                .build();
        DishEntity dish8 = DishEntity.builder()
                .name("dish03")
                .price(BigDecimal.valueOf(40))
                .labels(gson.toJson(ImmutableList.of(Label.VEGAN)))
                .subcategory(drinksSub3)
                .build();
        DishEntity dish9 = DishEntity.builder()
                .name("dish03")
                .price(BigDecimal.valueOf(40))
                .labels(gson.toJson(ImmutableList.of(Label.VEGAN)))
                .subcategory(drinksSub4)
                .build();

        return args -> {
            dishRepository.save(dish1);
            dishRepository.save(dish2);
            dishRepository.save(dish3);
            dishRepository.save(dish4);
            dishRepository.save(dish5);
            dishRepository.save(dish6);
            dishRepository.save(dish7);
            dishRepository.save(dish8);
            dishRepository.save(dish9);
            dishRepository.save(dish10);
            dishRepository.save(dish11);
        };
    }

}
