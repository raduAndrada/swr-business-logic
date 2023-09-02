package ro.swr.dishes.services;

import lombok.RequiredArgsConstructor;
import model.Dish;
import model.rest.SearchRequest;
import model.rest.SearchResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ro.swr.dishes.mappers.DishEntityToHistoryMapper;
import ro.swr.dishes.repository.DishHistoryRepository;
import ro.swr.dishes.repository.DishRepository;
import ro.swr.dishes.repository.entities.DishHistoryEntity;
import ro.swr.services.mappers.ModelEntityMapper;

import java.util.List;


@Component
@RequiredArgsConstructor
public class DishHistoryServiceImpl implements DishHistoryService {

    private final DishHistoryRepository historyRepository;
    private final DishRepository dishRepository;

    private static final DishEntityToHistoryMapper mapper = new DishEntityToHistoryMapper();
    private static final ModelEntityMapper<Dish, DishHistoryEntity> genericMapper = new ModelEntityMapper<>(Dish.class, DishHistoryEntity.class);

    @Override
    public SearchResponse<Dish> searchDish(SearchRequest<String> searchRequest) {
        List<DishHistoryEntity> histories = historyRepository.findByNameIgnoringCase(searchRequest.getFilterOptions());
        return new SearchResponse(new PageImpl(genericMapper.fromEntity(histories)));
    }

    @Override
    public boolean reinstateDish(String name) {
        List<DishHistoryEntity> histories = historyRepository.findByNameIgnoringCase(name);
        dishRepository.saveAll(mapper.fromHistory(histories));
        return histories.size() > 0;
    }

    @Override
    public SearchResponse<Dish> findAll(SearchRequest<String> searchRequest) {
        Pageable pageable =
                PageRequest.of(searchRequest.getOffset(), searchRequest.getSize());
        if (StringUtils.isBlank(searchRequest.getFilterOptions())) {
            return new SearchResponse<>(genericMapper.fromEntity(historyRepository.findAll(pageable)));
        } else {
            return new SearchResponse<>(genericMapper.fromEntity(historyRepository.findAllByNameIgnoringCase(pageable, searchRequest.getFilterOptions())));
        }
    }

    @Override
    public SearchResponse<Dish> findAll() {
        return new SearchResponse(
                new PageImpl(genericMapper.fromEntity((List<DishHistoryEntity>) historyRepository.findAll(Sort.by("name")))));

    }
}
