package ro.swr.dishes.rest;


import lombok.RequiredArgsConstructor;
import model.Category;
import model.Dish;
import model.DishMenu;
import model.rest.SearchRequest;
import model.rest.SearchResponse;
import org.springframework.web.bind.annotation.*;
import ro.swr.dishes.services.DishService;

import java.util.List;


@RestController
@RequestMapping(path = "/api/dishes", produces = "application/json")
@CrossOrigin
@RequiredArgsConstructor
public class DishController {

    private final DishService dishService;

    @PostMapping("/menu")
    public SearchResponse<Dish> getMenu(@RequestBody SearchRequest<String> searchRequest) {
        return dishService.getMenu(searchRequest);
    }

    @GetMapping("/menu")
    public DishMenu getMenu() {
        return dishService.getMenu();
    }

    @GetMapping("/categories")
    public List<Category> getCategories() {
        return dishService.getDishCategories();
    }

    @DeleteMapping("/{name}")
    public Long deleteByName(@PathVariable String name) {
        return dishService.deleteByName(name);
    }

    @PostMapping
    public Dish save(@RequestBody Dish dish) {
        return dishService.save(dish);
    }

    @PutMapping
    public Dish update(@RequestBody Dish dish) {
        return dishService.save(dish);
    }

    @PatchMapping
    public Dish patch(@RequestBody Dish dish) {
        return dishService.save(dish);
    }


}
