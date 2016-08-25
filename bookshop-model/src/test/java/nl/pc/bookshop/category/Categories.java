package nl.pc.bookshop.category;


import nl.pc.bookshop.category.model.Category;

public class Categories {

    private Categories() {
        //helper class
    }

    public static Category java() {
        return new Category("java");
    }
}
