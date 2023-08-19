package ro.swr.dishes.repositories;

import com.google.common.collect.Lists;
import model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import ro.swr.dishes.repository.DishRepository;
import ro.swr.dishes.repository.entities.DishEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static ro.swr.dishes.TestUtils.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DataJpaTest
public class DishRepositoryTest {

    public static final String DISH = "dish";
    public static final String SUGAR_FREE = "SUGAR_FREE";
    @Autowired
    private DishRepository dishRepository;

    @BeforeEach
    void setup() {
        dishRepository.saveAll(generateInitialDbValues());
    }

    @Test
    void testFindAllByNameIgnoringCase() {
        List<DishEntity> dishes = dishRepository.findAllByNameIgnoringCase("dish1");
        assertThat(dishes, hasSize(1));
    }

    @Test
    void testFindAllByNameIsContainingIgnoringCase() {
        List<DishEntity> dishes = dishRepository.findAllByNameIsContainingIgnoringCase(DISH);
        assertThat(dishes, hasSize(10));
    }

    @Test
    void testFindAllByPriceIsBetween() {
        List<DishEntity> dishes = dishRepository.findAllByPriceIsBetween(BigDecimal.valueOf(15), BigDecimal.valueOf(17));
        assertThat(dishes, hasSize(3));
    }

    @Test
    void testFindAllByLabelsContaining() {
        List<DishEntity> dishes = dishRepository.findAllByLabelsContaining(SUGAR_FREE);
        assertThat(dishes, hasSize(4));
    }

    @Test
    void testFindAllByCategory() {
        List<DishEntity> dishes = dishRepository.findAllByCategory(Category.DESSERT);
        assertThat(dishes, hasSize(4));
    }

    static List<DishEntity> generateInitialDbValues() {
        List<DishEntity> entities = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            String labels = "FISH";
            Category category = Category.MAIN_COURSE;
            String ingredientSrc = SINGLE_INGREDIENT;
            if (i % 3 == 0) {
                labels += "," + SUGAR_FREE;
                category = Category.DESSERT;
                ingredientSrc = MULTIPLE_INGREDIENTS;
            }
            entities.add(getTestDishEntity(
                    DISH + i,
                    BigDecimal.TEN.add(BigDecimal.valueOf(i)),
                    labels,
                    category,
                    ingredientSrc,
                    NUTRITIONAL_VALUE_FULL
            ));
        }
        return entities;
    }

}
