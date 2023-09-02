package ro.swr.staticdata.repository;

import org.springframework.data.repository.CrudRepository;
import ro.swr.staticdata.repository.entities.TeamBlockEntity;
import ro.swr.staticdata.repository.entities.TrendingBlockEntity;

import java.util.List;

public interface TeamBlockRepository extends CrudRepository<TeamBlockEntity, Long> {

}
