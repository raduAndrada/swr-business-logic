package ro.swr.menu.model;

import java.util.LinkedList;
import java.util.List;

public record Subcategory(
        String name,
        LinkedList<MenuItem> menuItems,
        List<String> img
) {
}
