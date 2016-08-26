package nl.pc.bookshop.category.repository;


import nl.pc.bookshop.category.model.Category;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@SuppressWarnings("JpaQlInspection")
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
        if (id == null) {
            return null;
        }
        return em.find(Category.class, id);
    }

    public void update(Category category) {
        em.merge(category);
    }

    public List<Category> findAll(String orderField) {
        return em.createQuery("Select e From Category e Order by e." + orderField).getResultList();
    }

    public boolean alreadyExists(Category category) {

        StringBuilder jpql = new StringBuilder();
        jpql.append("Select 1 from Category e where e.name = :name");
        if (category.getId() != null) {
            jpql.append(" And e.id != :id");
        }
        Query query = em.createQuery(jpql.toString());
        query.setParameter("name", category.getName());
        if (category.getId() != null) {
            query.setParameter("id", category.getId());
        }
        return query.setMaxResults(1).getResultList().size() > 0;
    }

    public boolean existsById(final Long id) {
        return em.createQuery("Select 1 from Category e where e.id = :id")
                .setParameter("id", id)
                .setMaxResults(1)
                .getResultList().size() > 0;
    }
}
