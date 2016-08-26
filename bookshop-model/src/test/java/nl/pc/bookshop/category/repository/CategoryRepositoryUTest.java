package nl.pc.bookshop.category.repository;


import nl.pc.bookshop.category.model.Category;
import nl.pc.bookshop.commontests.utils.DBCommandTransactionalExecutor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

import static nl.pc.bookshop.category.Categories.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class CategoryRepositoryUTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private CategoryRepository categoryRepository;
    private DBCommandTransactionalExecutor dbCommandTransactionalExecutor;

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("bookshopPU");
        em = emf.createEntityManager();
        categoryRepository = new CategoryRepository(em);

        dbCommandTransactionalExecutor = new DBCommandTransactionalExecutor(em);
    }

    @After
    public void tearDown() {
        em.close();
        emf.close();
    }

    @Test
    public void addCategoryAndFindIt() {
        final Long categoryId = dbCommandTransactionalExecutor.execute(() -> categoryRepository.addCategory(java()).getId());
        assertThat(categoryId, is(notNullValue()));
        final Category category = categoryRepository.findById(categoryId);
        assertThat(category.getName(), is(equalTo(java().getName())));
    }

    @Test
    public void findCategoryByIdNotFound() {
        final Category category = categoryRepository.findById(999L);
        assertThat(category, is(nullValue()));
    }

    @Test
    public void findCategoryByIdWithNullId() {
        final Category category = categoryRepository.findById(null);
        assertThat(category, is(nullValue()));
    }

    @Test
    public void updateCategory() {
        final Long categoryId = dbCommandTransactionalExecutor.execute(() -> categoryRepository.addCategory(java())).getId();
        Category category = categoryRepository.findById(categoryId);
        assertThat(category.getName(), is(equalTo(java().getName())));


        category.setName(cleanCode().getName());
        //now we modify this category's name
        dbCommandTransactionalExecutor.execute(() -> {
            categoryRepository.update(category);
            return null;
        });

        Category categoryAfterUpdate = categoryRepository.findById(categoryId);
        assertThat(categoryAfterUpdate.getName(), is(equalTo(cleanCode().getName())));
    }

    @Test
    public void findAllCategories() {
        dbCommandTransactionalExecutor.execute(() -> {
            allCategories().forEach(categoryRepository::addCategory);
            return null;
        });
        final List<Category> categories = categoryRepository.findAll("name");
        assertThat(categories.size(), is(equalTo(4)));
        assertThat(categories.get(0).getName(), is(equalTo(architecture().getName())));
        assertThat(categories.get(1).getName(), is(equalTo(cleanCode().getName())));
        assertThat(categories.get(2).getName(), is(equalTo(java().getName())));
        assertThat(categories.get(3).getName(), is(equalTo(networks().getName())));
    }

    @Test
    public void alreadyExistsForAdd() {
        dbCommandTransactionalExecutor.execute(() -> categoryRepository.addCategory(java()));
        assertThat(categoryRepository.alreadyExists(java()), is(equalTo(true)));
        assertThat(categoryRepository.alreadyExists(cleanCode()), is(equalTo(false)));
    }

    @Test
    public void existsById() {
        final Long categoryId = dbCommandTransactionalExecutor.execute(() -> categoryRepository.addCategory(java()).getId());
        assertThat(categoryRepository.existsById(categoryId), is(equalTo(true)));
        assertThat(categoryRepository.existsById(999L), is(equalTo(false)));
    }
}
