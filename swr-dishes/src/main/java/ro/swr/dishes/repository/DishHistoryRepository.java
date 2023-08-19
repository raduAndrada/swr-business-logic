package ro.swr.dishes.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ro.swr.dishes.repository.entities.DishEntity;
import ro.swr.dishes.repository.entities.DishHistoryEntity;
import ro.swr.dishes.repository.entities.RecipeEntity;

import java.util.List;

public interface DishHistoryRepository extends PagingAndSortingRepository<DishHistoryEntity, Long>,
        CrudRepository<DishHistoryEntity, Long> {

    List<DishHistoryEntity> findByNameIgnoringCase(String name);

    Page<DishHistoryEntity> findAllByNameIgnoringCase(Pageable pageable, String name);

    List<DishHistoryEntity> findByNameIsContainingIgnoringCase(String name);
}
