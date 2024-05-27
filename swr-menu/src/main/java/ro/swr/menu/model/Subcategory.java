package ro.swr.menu.model;

import java.util.LinkedList;

public record Subcategory(
        String name,
        LinkedList<MenuItem> menuItems
) {
}
