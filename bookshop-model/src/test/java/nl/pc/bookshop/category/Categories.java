package nl.pc.bookshop.category;


import nl.pc.bookshop.category.model.Category;

import java.util.Arrays;
import java.util.List;

public class Categories {

    private Categories() {
        //helper class
    }

    public static Category java() {
        return new Category("Java");
    }

    public static Category cleanCode() {
        return new Category("Clean Code");
    }

    public static Category architecture() {
        return new Category("Architecture");
    }

    public static Category networks() {
        return new Category("Networks");
    }

    public static List<Category> allCategories() {
        return Arrays.asList(java(), cleanCode(), architecture(), networks());
    }

    public static Category mapToCategoryWithId(Category category, Long id) {
        category.setId(id);
        return category;
    }
}
