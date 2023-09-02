package ro.swr.dishes.repository;

import org.springframework.data.repository.CrudRepository;
import ro.swr.dishes.repository.entities.ReservationEntity;
import ro.swr.dishes.repository.entities.TableEntity;

import java.sql.Date;
import java.util.List;

public interface ReservationRepository extends CrudRepository<ReservationEntity, Long> {

    List<ReservationEntity> findAllByDateAndTable(Date date, TableEntity table);

    List<ReservationEntity> findAllByDateAndTableSpotsBetween(Date date, Integer lowerBound, Integer upperBound);


}
