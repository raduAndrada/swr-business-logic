package ro.swr.dishes.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ro.swr.dishes.repository.entities.DishEntity;

import java.math.BigDecimal;
import java.util.List;

public interface DishRepository extends PagingAndSortingRepository<DishEntity, Long>,
        CrudRepository<DishEntity, Long> {

    Page<DishEntity> findAllByNameIgnoringCase(Pageable pageable, String name);

    List<DishEntity> findAllByNameIgnoringCase(String name);

    List<DishEntity> findAllByNameIsContainingIgnoringCase(String name);

    List<DishEntity> findAllByPriceIsBetween(BigDecimal lowerBound, BigDecimal higherBound);

    List<DishEntity> findAllByLabelsContaining(String label);

    int deleteAllByNameIgnoringCase(String name);

}
