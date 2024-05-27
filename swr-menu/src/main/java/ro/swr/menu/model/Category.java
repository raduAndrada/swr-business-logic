package ro.swr.menu.model;

import java.util.LinkedList;

public record Category(
        String name,
        LinkedList<Subcategory> subcategories
) {


}
