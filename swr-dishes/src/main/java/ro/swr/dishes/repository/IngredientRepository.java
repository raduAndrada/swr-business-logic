package ro.swr.dishes.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ro.swr.dishes.repository.entities.IngredientEntity;

import java.util.List;

public interface IngredientRepository extends CrudRepository<IngredientEntity, Long>,
        PagingAndSortingRepository<IngredientEntity, Long> {

    List<IngredientEntity> findByShortNameIgnoringCase(String name);

    List<IngredientEntity> findByRecipeNameIsContainingIgnoringCase(String recipeName);
}
