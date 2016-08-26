package nl.pc.bookshop.commontests.utils;


import javax.persistence.EntityManager;

public class DBCommandTransactionalExecutor {

    private final EntityManager em;

    public DBCommandTransactionalExecutor(EntityManager em) {
        this.em = em;
    }

    public <T> T execute(DBCommand<T> dbCommand) {
        try {
            em.getTransaction().begin();
            T result = dbCommand.execute();
            em.getTransaction().commit();
            em.clear();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            throw new IllegalStateException(e);
        }
    }

}
