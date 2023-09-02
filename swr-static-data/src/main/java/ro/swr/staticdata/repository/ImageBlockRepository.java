package ro.swr.staticdata.repository;

import org.springframework.data.repository.CrudRepository;
import ro.swr.staticdata.repository.entities.ImageBlockEntity;

import java.util.List;

public interface ImageBlockRepository extends CrudRepository<ImageBlockEntity, Long> {

    List<ImageBlockEntity> findAllByOrigin(String origin);
}
