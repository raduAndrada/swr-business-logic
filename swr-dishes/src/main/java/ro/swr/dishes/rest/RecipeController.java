package ro.swr.dishes.rest;

import lombok.RequiredArgsConstructor;
import model.Recipe;
import model.rest.SearchResponse;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;
import ro.swr.dishes.repository.entities.RecipeEntity;
import ro.swr.dishes.services.SwrServiceBase;


@RestController
@RequestMapping(path = "/api/recipes", produces = "application/json")
@CrossOrigin(origins = {"http://tacocloud:8080", "http://tacocloud.com"})
@RequiredArgsConstructor
public class RecipeController {

    private final SwrServiceBase<Recipe, RecipeEntity> recipeService;

    @GetMapping
    public SearchResponse<Recipe> findAll() {
        return new SearchResponse(new PageImpl(recipeService.findAll()));
    }

    @PostMapping
    public Recipe save(@RequestBody Recipe recipe) {
        return recipeService.save(recipe);
    }

    @PutMapping
    public Recipe update(@RequestBody Recipe recipe) {
        return recipeService.save(recipe);
    }


}