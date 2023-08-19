package ro.swr.dishes.repository;

import org.springframework.data.repository.CrudRepository;
import ro.swr.dishes.repository.entities.RecipeEntity;

import java.util.List;

public interface RecipeRepository extends CrudRepository<RecipeEntity, Long> {

    List<RecipeEntity> findByNameIgnoringCase(String name);

    List<RecipeEntity> findByNameIsContainingIgnoringCase(String name);
}
