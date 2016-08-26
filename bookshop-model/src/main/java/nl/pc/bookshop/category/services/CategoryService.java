package nl.pc.bookshop.category.services;


import nl.pc.bookshop.category.exception.CategoryExistsException;
import nl.pc.bookshop.category.model.Category;
import nl.pc.bookshop.common.exeption.FieldNotValidException;

public interface CategoryService {

    Category add(Category category) throws FieldNotValidException, CategoryExistsException;
}
