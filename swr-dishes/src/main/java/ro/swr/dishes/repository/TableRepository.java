package ro.swr.dishes.repository;

import org.springframework.data.repository.CrudRepository;
import ro.swr.dishes.repository.entities.TableEntity;

import java.util.List;

public interface TableRepository extends CrudRepository<TableEntity, Long> {

    List<TableEntity> findAllBySpots(Integer spots);

    List<TableEntity> findAllBySpotsBetween(Integer lowerBound, Integer upperBound);
}
