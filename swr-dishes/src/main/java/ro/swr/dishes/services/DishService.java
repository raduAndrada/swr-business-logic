package ro.swr.dishes.services;

import model.Dish;
import model.rest.DishFilterOptions;
import model.rest.SearchRequest;
import model.rest.SearchResponse;
import ro.swr.dishes.repository.entities.DishEntity;

public interface DishService extends SwrServiceBase<Dish, DishEntity>{

    SearchResponse<Dish> searchDish(SearchRequest<DishFilterOptions> searchRequest);

    long deleteByName(String name);

    SearchResponse<Dish> getMenu(SearchRequest<String> searchRequest);

    SearchResponse<Dish> getMenu();


}
