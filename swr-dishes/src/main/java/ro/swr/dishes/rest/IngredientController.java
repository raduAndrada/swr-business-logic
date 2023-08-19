package ro.swr.dishes.rest;

import lombok.RequiredArgsConstructor;
import model.Ingredient;
import model.rest.SearchResponse;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;
import ro.swr.dishes.repository.entities.IngredientEntity;
import ro.swr.dishes.services.SwrServiceBase;

@RestController
@RequestMapping(path = "/api/ingredients", produces = "application/json")
@CrossOrigin(origins = {"http://tacocloud:8080", "http://tacocloud.com"})
@RequiredArgsConstructor
public class IngredientController {

    private final SwrServiceBase<Ingredient, IngredientEntity> ingredientService;

    @GetMapping
    public SearchResponse<Ingredient> findAll() {
        return new SearchResponse(new PageImpl(ingredientService.findAll()));
    }

    @PostMapping
    public Ingredient save(@RequestBody Ingredient ingredient) {
        return ingredientService.save(ingredient);
    }

    @PutMapping
    public Ingredient update(@RequestBody Ingredient ingredient) {
        return ingredientService.save(ingredient);
    }


}
