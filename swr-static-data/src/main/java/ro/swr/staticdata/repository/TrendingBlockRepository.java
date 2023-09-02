package ro.swr.staticdata.repository;

import org.springframework.data.repository.CrudRepository;
import ro.swr.staticdata.repository.entities.TrendingBlockEntity;

import java.util.List;

public interface TrendingBlockRepository extends CrudRepository<TrendingBlockEntity, Long> {

    List<TrendingBlockEntity> findAllByInTrending(boolean inTrending);

    int deleteAllByInTrending(boolean inTrending);
}
