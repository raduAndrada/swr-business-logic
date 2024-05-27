package ro.swr.menu.services;

import ro.swr.menu.model.Category;

import java.util.Collection;

public interface MenuParser {

    Collection<Category> parseMenu();
}
