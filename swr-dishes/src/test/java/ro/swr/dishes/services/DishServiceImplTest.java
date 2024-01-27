package ro.swr.dishes.services;

import com.google.common.collect.Lists;
import com.google.gson.reflect.TypeToken;
import model.Category;
import model.Dish;
import model.Label;
import model.rest.DishFilterOptions;
import model.rest.SearchRequest;
import model.rest.SearchResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.swr.dishes.mappers.DishEntityToHistoryMapper;
import ro.swr.dishes.repository.CategoryRepository;
import ro.swr.dishes.repository.DishHistoryRepository;
import ro.swr.dishes.repository.DishRepository;
import ro.swr.dishes.repository.entities.DishEntity;
import ro.swr.dishes.repository.entities.DishHistoryEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static ro.swr.dishes.TestUtils.*;

@ExtendWith(MockitoExtension.class)
@Disabled
class DishServiceImplTest {

    public static final String DISH = "dish";
    private static final DishEntityToHistoryMapper dishHistoryMapper = new DishEntityToHistoryMapper();

    @Mock
    private DishHistoryRepository historyRepository;

    @Mock
    private DishRepository dishRepository;

    @Mock
    private CategoryRepository categoryRepository;

    private DishService dishService = new DishServiceImpl(dishRepository, historyRepository, categoryRepository);

    private final List<DishEntity> mockedResponse =
            getTestListFromJson(MULTIPLE_DISHES, new TypeToken<List<DishEntity>>() {
            }.getType());

    @BeforeEach
    void setup() {
        dishService = new DishServiceImpl(dishRepository, historyRepository, categoryRepository);
    }


    @ParameterizedTest
    @MethodSource("provideSearchDishData")
    void testSearchDish(
            Predicate<DishEntity> filter,
            SearchRequest<DishFilterOptions> searchRequest,
            BigDecimal searchPriceLowerBound,
            BigDecimal searchPriceUpperBound,
            long expectedSize) {
        when(dishRepository.findAllByPriceIsBetween(searchPriceLowerBound, searchPriceUpperBound))
                .thenReturn(mockedResponse.stream().filter(filter).collect(Collectors.toList()));


        SearchResponse<Dish> response = dishService.searchDish(searchRequest);
        assertEquals(expectedSize, response.getData().getTotalElements());
        verifyNoInteractions(historyRepository);
        verify(dishRepository).findAllByPriceIsBetween(searchPriceLowerBound, searchPriceUpperBound);
    }

    @Test
    void testDeleteByName() {
        when(dishRepository.findAllByNameIgnoringCase(DISH))
                .thenReturn(mockedResponse);
        List<DishHistoryEntity> histories = dishHistoryMapper.toHistory(mockedResponse);

        long deleted = dishService.deleteByName(DISH);
        assertEquals(4, deleted);

        verify(dishRepository).findAllByNameIgnoringCase(DISH);
        verify(historyRepository).saveAll(histories);
        verify(dishRepository).deleteAll(mockedResponse);
    }

    private static Stream<Arguments> provideSearchDishData() {
        Predicate<DishEntity> allowAllPred = (t) -> true;
        SearchRequest emptyRequest = buildSearchRequest(new DishFilterOptions());

        BigDecimal upperBound = BigDecimal.valueOf(75);
        Predicate<DishEntity> upperBoundPred = (t) -> upperBound.compareTo(t.getPrice()) >= 0;
        SearchRequest upperBoundRequest =
                buildSearchRequest(DishFilterOptions.builder()
                        .upperBound(upperBound)
                        .build());

        Predicate<DishEntity> lowerBoundPred = (t) -> upperBound.compareTo(t.getPrice()) < 0;
        SearchRequest lowerBoundRequest =
                buildSearchRequest(DishFilterOptions.builder()
                        .lowerBound(upperBound)
                        .build());

        Predicate<DishEntity> boundedPred = (t) -> upperBound.compareTo(t.getPrice()) >= 0 && BigDecimal.TEN.compareTo(t.getPrice()) <= 0;
        SearchRequest boundedRequest =
                buildSearchRequest(DishFilterOptions.builder()
                        .lowerBound(BigDecimal.TEN)
                        .upperBound(upperBound)
                        .build());


        SearchRequest labeledRequest =
                buildSearchRequest(DishFilterOptions.builder()
                        .label(Lists.newArrayList(Label.FISH))
                        .build());

        SearchRequest categoryRequest =
                buildSearchRequest(DishFilterOptions.builder()
                        .category(Category.builder()
                                .name("SANDWICHES")
                                .build())
                        .build());

        SearchRequest nameRequest =
                buildSearchRequest(DishFilterOptions.builder()
                        .name(DISH)
                        .build());

        SearchRequest complexRequest =
                buildSearchRequest(DishFilterOptions.builder()
                        .name(DISH)
                        .category(Category.builder()
                                .name("BRUNCH")
                                .build())
                        .label(Lists.newArrayList(Label.SUGAR_FREE))
                        .build());


        return Stream.of(
                Arguments.of(allowAllPred, emptyRequest, BigDecimal.ZERO, BigDecimal.valueOf(Double.MAX_VALUE), 4l),
                Arguments.of(upperBoundPred, upperBoundRequest, BigDecimal.ZERO, upperBound, 2l),
                Arguments.of(lowerBoundPred, lowerBoundRequest, upperBound, BigDecimal.valueOf(Double.MAX_VALUE), 2l),
                Arguments.of(boundedPred, boundedRequest, BigDecimal.TEN, upperBound, 2l),
                Arguments.of(allowAllPred, labeledRequest, BigDecimal.ZERO, BigDecimal.valueOf(Double.MAX_VALUE), 1l),
                Arguments.of(allowAllPred, categoryRequest, BigDecimal.ZERO, BigDecimal.valueOf(Double.MAX_VALUE), 2l),
                Arguments.of(allowAllPred, nameRequest, BigDecimal.ZERO, BigDecimal.valueOf(Double.MAX_VALUE), 4l),
                Arguments.of(allowAllPred, complexRequest, BigDecimal.ZERO, BigDecimal.valueOf(Double.MAX_VALUE), 4l)
        );
    }

}