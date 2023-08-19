package ro.swr.dishes.services;

import model.Dish;
import model.rest.SearchRequest;
import model.rest.SearchResponse;

public interface DishHistoryService {

    SearchResponse<Dish> searchDish(SearchRequest<String> searchRequest);

    boolean reinstateDish(String name);

    SearchResponse<Dish> findAll(SearchRequest<String> searchRequest);

    SearchResponse<Dish> findAll();
}
