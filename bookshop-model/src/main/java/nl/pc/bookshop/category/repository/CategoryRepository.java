package nl.pc.bookshop.category.repository;


import nl.pc.bookshop.category.model.Category;

import javax.persistence.EntityManager;

public class CategoryRepository {

    private EntityManager em;

    public CategoryRepository(final EntityManager em) {
        this.em = em;
    }

    public Category addCategory(Category category) {
        em.persist(category);
        return category;
    }

    public Category findById(Long id) {
        return em.find(Category.class, id);
    }
}
