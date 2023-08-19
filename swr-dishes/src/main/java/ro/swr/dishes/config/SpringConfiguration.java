package ro.swr.dishes.config;

import model.Ingredient;
import model.Recipe;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.swr.dishes.mappers.ModelEntityMapper;
import ro.swr.dishes.repository.IngredientRepository;
import ro.swr.dishes.repository.RecipeRepository;
import ro.swr.dishes.repository.entities.IngredientEntity;
import ro.swr.dishes.repository.entities.RecipeEntity;
import ro.swr.dishes.services.SwrServiceBase;
import ro.swr.dishes.services.SwrServiceBaseImpl;

@Configuration
public class SpringConfiguration {

    @Bean
    public SwrServiceBase ingredientService(IngredientRepository ingredientRepository) {
        return new SwrServiceBaseImpl(ingredientRepository, new ModelEntityMapper(Ingredient.class, IngredientEntity.class));
    }

    @Bean
    public SwrServiceBase recipeService(RecipeRepository recipeRepository) {
        return new SwrServiceBaseImpl(recipeRepository, new ModelEntityMapper(Recipe.class, RecipeEntity.class));
    }

}
