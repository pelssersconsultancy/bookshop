package nl.pc.bookshop.category.services.impl;

import nl.pc.bookshop.category.exception.CategoryExistsException;
import nl.pc.bookshop.category.model.Category;
import nl.pc.bookshop.category.repository.CategoryRepository;
import nl.pc.bookshop.category.services.CategoryService;
import nl.pc.bookshop.common.exeption.FieldNotValidException;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Iterator;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {

    Validator validator;

    CategoryRepository categoryRepository;

    @Override
    public Category add(Category category) {
        Set<ConstraintViolation<Category>> errors = validator.validate(category);
        Iterator<ConstraintViolation<Category>> errorIterator = errors.iterator();
        if (errorIterator.hasNext()) {
            ConstraintViolation<Category> violation = errorIterator.next();
            throw new FieldNotValidException(violation.getPropertyPath().toString(), violation.getMessage());
        }
        if (categoryRepository.alreadyExists(category)) {
            throw new CategoryExistsException();
        }
        return categoryRepository.addCategory(category);
    }
}
