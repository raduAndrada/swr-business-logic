package ro.swr.menu.rest;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.swr.menu.model.Category;
import ro.swr.menu.services.MenuParser;

import java.util.Collection;


@RestController
@RequestMapping(path = "/api/menu", produces = "application/json")
@CrossOrigin
@RequiredArgsConstructor
public class MenuController {

    private final MenuParser dishService;

    @GetMapping
    public Collection<Category> getMenu() {
        return dishService.parseMenu();
    }


}
