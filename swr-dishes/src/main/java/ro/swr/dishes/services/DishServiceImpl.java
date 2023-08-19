package ro.swr.dishes.services;

import lombok.extern.slf4j.Slf4j;
import model.Category;
import model.Dish;
import model.Label;
import model.rest.DishFilterOptions;
import model.rest.SearchRequest;
import model.rest.SearchResponse;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ro.swr.dishes.mappers.DishEntityToHistoryMapper;
import ro.swr.dishes.mappers.ModelEntityMapper;
import ro.swr.dishes.repository.DishHistoryRepository;
import ro.swr.dishes.repository.DishRepository;
import ro.swr.dishes.repository.entities.DishEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Slf4j
@Component
public class DishServiceImpl extends SwrServiceBaseImpl<Dish, DishEntity> implements DishService {

    private DishRepository dishRepository;
    private DishHistoryRepository dishHistoryRepository;

    private static final DishEntityToHistoryMapper dishHistoryMapper = new DishEntityToHistoryMapper();
    private static final ModelEntityMapper<Dish, DishEntity> mapper = new ModelEntityMapper(Dish.class, DishEntity.class);

    public DishServiceImpl(DishRepository dishRepository,
                           DishHistoryRepository dishHistoryRepository) {
        super(dishRepository, mapper);
        this.dishRepository = dishRepository;
        this.dishHistoryRepository = dishHistoryRepository;
    }


    @Override
    public SearchResponse<Dish> searchDish(SearchRequest<DishFilterOptions> searchRequest) {
        return performSearch(searchRequest);
    }

    @Override
    public long deleteByName(String name) {
        List<DishEntity> toDelete = dishRepository.findAllByNameIgnoringCase(name);
        dishHistoryRepository.saveAll(dishHistoryMapper.toHistory(toDelete));
        dishRepository.deleteAll(toDelete);
        return toDelete.size();
    }

    @Override
    public SearchResponse<Dish> getMenu(SearchRequest<String> searchRequest) {
        Pageable pageable =
                PageRequest.of(searchRequest.getOffset(), searchRequest.getSize(), Sort.by(searchRequest.getSortBy()));
        return new SearchResponse(mapper.fromEntity(dishRepository.findAll(pageable)));
    }

    @Override
    public SearchResponse<Dish> getMenu() {
        Pageable pageable = PageRequest.of(0, 15, Sort.by("category").ascending());
        return new SearchResponse(mapper.fromEntity(dishRepository.findAll(pageable)));
    }

    private SearchResponse<Dish> performSearch(SearchRequest<DishFilterOptions> searchRequest) {
        log.info("Searching dishes with: {}", searchRequest);
        DishFilterOptions options = searchRequest.getFilterOptions();
        BigDecimal lowerBound = options.getLowerBound();
        BigDecimal upperBound = options.getUpperBound();
        boolean lowerBoundNull = Objects.isNull(lowerBound);
        boolean upperBoundNull = Objects.isNull(upperBound);
        if (lowerBoundNull) {
            lowerBound = BigDecimal.ZERO;
        }
        if (upperBoundNull) {
            upperBound = BigDecimal.valueOf(Double.MAX_VALUE);
        }
        List<Dish> dishes =
                mapper.fromEntity(dishRepository.findAllByPriceIsBetween(lowerBound, upperBound));

        Predicate<Dish> filter = (d) -> {
            boolean add = true;
            String filterName = options.getName();
            Category filterCategory = options.getCategory();
            List<Label> filterLabels = options.getLabel();
            if (StringUtils.isNotBlank(filterName)) {
                if (!d.getName().contains(filterName)) {
                    add = false;
                }
            } else if (Objects.nonNull(filterCategory)) {
                if (!d.getCategory().equals(filterCategory)) {
                    add = false;
                }
            } else if (!CollectionUtils.isEmpty(filterLabels)) {
                if (CollectionUtils.isEmpty(d.getLabels())) {
                    add = false;
                } else {
                    add = d.getLabels().stream()
                            .filter(label -> filterLabels.parallelStream().anyMatch(l -> l.equals(label)))
                            .findAny()
                            .isPresent();
                }
            }
            return add;
        };
        SearchResponse response = new SearchResponse(
                new PageImpl(dishes.stream()
                        .filter(filter)
                        .collect(Collectors.toList())));
        log.info("Search response is: {}", response);
        return response;

    }


}
