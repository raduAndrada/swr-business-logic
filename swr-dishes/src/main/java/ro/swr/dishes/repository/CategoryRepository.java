package ro.swr.dishes.repository;

import org.springframework.data.repository.CrudRepository;
import ro.swr.dishes.repository.entities.CategoryEntity;
import ro.swr.dishes.repository.entities.RecipeEntity;

import java.util.List;

public interface CategoryRepository extends CrudRepository<CategoryEntity, String> {
}
