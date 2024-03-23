package ro.swr.dishes.services;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import model.*;
import model.rest.DishFilterOptions;
import model.rest.SearchRequest;
import model.rest.SearchResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ro.swr.dishes.mappers.DishEntityToHistoryMapper;
import ro.swr.dishes.mappers.ModelEntityMapper;
import ro.swr.dishes.repository.CategoryRepository;
import ro.swr.dishes.repository.DishHistoryRepository;
import ro.swr.dishes.repository.DishRepository;
import ro.swr.dishes.repository.entities.CategoryEntity;
import ro.swr.dishes.repository.entities.DishEntity;
import ro.swr.services.SwrServiceBaseImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Slf4j
@Component
public class DishServiceImpl extends SwrServiceBaseImpl<Dish, DishEntity> implements DishService {

    private DishRepository dishRepository;
    private DishHistoryRepository dishHistoryRepository;
    private CategoryRepository categoryRepository;

    private static final DishEntityToHistoryMapper dishHistoryMapper = new DishEntityToHistoryMapper();
    private static final ModelEntityMapper<Dish, DishEntity> mapper = new ModelEntityMapper(Dish.class, DishEntity.class);
    private static final ModelEntityMapper<Category, CategoryEntity> categoryMapper = new ModelEntityMapper<>(Category.class, CategoryEntity.class);

    public DishServiceImpl(DishRepository dishRepository,
                           DishHistoryRepository dishHistoryRepository,
                           CategoryRepository categoryRepository) {
        super(dishRepository, new ro.swr.services.mappers.ModelEntityMapper(Dish.class, DishEntity.class));
        this.dishRepository = dishRepository;
        this.dishHistoryRepository = dishHistoryRepository;
        this.categoryRepository = categoryRepository;
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
    public DishMenu getMenu() {
        List<Dish> dishes = mapper.fromEntity((List<DishEntity>) dishRepository.findAll(Sort.by("price").ascending()));
        Map<Subcategory, List<Dish>> dishesMenu = Maps.newHashMap();
        dishes.stream()
                .forEach(
                        d -> {
                            if (!dishesMenu.containsKey(d.getSubcategory())) {
                                dishesMenu.put(d.getSubcategory(), Lists.newArrayList(d));
                            } else {
                                List<Dish> temp = dishesMenu.get(d.getSubcategory());
                                temp.add(d);
                                dishesMenu.put(d.getSubcategory(), temp);
                            }
                        }
                );

        return DishMenu.builder()
                .subcategoryMenu(dishesMenu.entrySet().stream().map(entry -> DishWrapper.builder()
                        .subcategory(entry.getKey())
                        .dishes(entry.getValue())
                        .build())
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public List<Category> getDishCategories() {
        return categoryMapper.fromEntity((List<CategoryEntity>) categoryRepository.findAll());
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
                if (!d.getSubcategory().getCategory().equals(filterCategory)) {
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
