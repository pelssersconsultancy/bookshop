package nl.pc.bookshop.category.repository;


import nl.pc.bookshop.category.model.Category;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static nl.pc.bookshop.category.Categories.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class CategoryRepositoryUTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private CategoryRepository categoryRepository;

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("bookshopPU");
        em = emf.createEntityManager();
        categoryRepository = new CategoryRepository(em);
    }

    @After
    public void tearDown() {
        em.close();
        emf.close();
    }

    @Test
    public void addCategoryAndFindIt() {
        Long categoryId = null;
        try {
            em.getTransaction().begin();
            categoryId = categoryRepository.addCategory(java()).getId();
            assertThat(categoryId, is(notNullValue()));
            em.getTransaction().commit();
            em.clear();
        } catch (Exception e) {
            fail("Exception should not have occurred");
            e.printStackTrace();
            em.getTransaction().rollback();
        }

        Category category = categoryRepository.findById(categoryId);
        assertThat("java", is(equalTo(category.getName())));
    }
}
