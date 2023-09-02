package ro.swr.dishes.services;

import model.Category;
import model.Dish;
import model.DishMenu;
import model.rest.DishFilterOptions;
import model.rest.SearchRequest;
import model.rest.SearchResponse;
import ro.swr.dishes.repository.entities.DishEntity;
import ro.swr.services.SwrServiceBase;

import java.util.List;

public interface DishService extends SwrServiceBase<Dish, DishEntity> {

    SearchResponse<Dish> searchDish(SearchRequest<DishFilterOptions> searchRequest);

    long deleteByName(String name);

    SearchResponse<Dish> getMenu(SearchRequest<String> searchRequest);

    DishMenu getMenu();

    List<Category> getDishCategories();


}
