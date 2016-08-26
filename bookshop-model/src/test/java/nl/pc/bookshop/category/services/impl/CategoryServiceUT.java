package nl.pc.bookshop.category.services.impl;


import nl.pc.bookshop.category.exception.CategoryExistsException;
import nl.pc.bookshop.category.model.Category;
import nl.pc.bookshop.category.repository.CategoryRepository;
import nl.pc.bookshop.category.services.CategoryService;
import nl.pc.bookshop.common.exeption.FieldNotValidException;
import org.junit.Before;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
import static nl.pc.bookshop.category.Categories.*;

public class CategoryServiceUT {

    private CategoryService categoryService;
    private CategoryRepository categoryRepository;
    private Validator validator;

    @Before
    public void setUp() {
        categoryService = new CategoryServiceImpl();
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        ( (CategoryServiceImpl)categoryService).validator = validator;
        categoryRepository = mock(CategoryRepository.class);
        ( (CategoryServiceImpl)categoryService).categoryRepository = categoryRepository;
    }

    @Test
    public void addCategoryWithNoName() {
         addCategoryWithInvalidName(null);
    }

    @Test
    public void addCategoryWithTooShortName() {
        addCategoryWithInvalidName("A");
    }

    @Test
    public void addCategoryWithTooLongName() {
        addCategoryWithInvalidName("MyNameIsTooLongForYouToRemember");
    }

    @Test(expected = CategoryExistsException.class)
    public void addCategoryWithExistingName() {
        when(categoryRepository.alreadyExists(java())).thenReturn(true);
        categoryService.add(java());
    }

    @Test
    public void addValidCategory() {
        when(categoryRepository.alreadyExists(java())).thenReturn(false);
        when(categoryRepository.addCategory(java())).thenReturn(mapToCategoryWithId(java(), 1L));
        Category category = categoryService.add(java());
        assertThat(category.getId(), is(equalTo(1L)));
    }

    public void addCategoryWithInvalidName(String name) {
        try {
            categoryService.add(new Category(name));
            fail("An error should have been thrown");
        } catch(FieldNotValidException e) {
            assertThat(e.getFieldName(), is(equalTo("name")));
        }
    }
}
