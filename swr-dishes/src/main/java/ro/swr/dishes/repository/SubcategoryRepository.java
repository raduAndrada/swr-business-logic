package ro.swr.dishes.repository;

import org.springframework.data.repository.CrudRepository;
import ro.swr.dishes.repository.entities.CategoryEntity;
import ro.swr.dishes.repository.entities.SubcategoryEntity;

public interface SubcategoryRepository extends CrudRepository<SubcategoryEntity, String> {
}
