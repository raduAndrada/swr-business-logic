package ro.swr.dishes.rest;


import lombok.RequiredArgsConstructor;
import model.Dish;
import model.rest.SearchRequest;
import model.rest.SearchResponse;
import org.springframework.web.bind.annotation.*;
import ro.swr.dishes.services.DishHistoryService;

@RestController
@RequestMapping(path="/api/history", produces="application/json")
@CrossOrigin(origins={"http://tacocloud:8080", "http://tacocloud.com"})
@RequiredArgsConstructor
public class DishHistoryController {

    private final DishHistoryService historyService;

    @PostMapping("/reinstate/{name}")
    public Boolean reinstate(@PathVariable String name) {
        return historyService.reinstateDish(name);
    }

    @PostMapping("/search")
    public SearchResponse<Dish> search(@RequestBody SearchRequest<String> searchRequest) {
        return historyService.searchDish(searchRequest);
    }

    @PostMapping
    public SearchResponse<Dish> findAll(@RequestBody SearchRequest<String> searchRequest) {
        return historyService.findAll(searchRequest);
    }

    @GetMapping
    public SearchResponse<Dish> findAll() {
        return historyService.findAll();
    }



}
