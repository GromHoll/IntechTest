package com.intech.server.db;

import com.intech.server.db.models.Content;
import com.intech.server.db.models.Reader;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {
    private static final DBHelper dbHelper = new DBHelper();

    public static final String PERSISTENCE_UNIT_NAME = "PersistenceUnit";

    private final EntityManager em;
    private final CriteriaBuilder cb;

    public static DBHelper getInstance() {
        return dbHelper;
    }

    private DBHelper() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        em = factory.createEntityManager();
        cb = em.getCriteriaBuilder();

        fillContent();
    }

    public void fillContent() {
        Query q = em.createQuery("SELECT count(c) FROM Content c");
        Number result = (Number) q.getSingleResult();

        if(result.intValue() == 0) {
            List<Content> list = new ArrayList<Content>();
            list.add(new Content("Hello", "Hello, World!"));
            list.add(new Content("Test", "Test Task For Intech"));
            list.add(new Content("Author", "Pavlov Nikita"));
            list.add(new Content("Code", "4 8 15 16 23 42"));
            list.add(new Content("Привет", "Привет, мир!"));
            list.add(new Content("Name", "Text"));

            em.getTransaction().begin();
            for(Content c : list) {
                em.merge(c);
            }
            em.getTransaction().commit();
        }
    }

    public Reader findReader(String username) {
        Reader reader = null;
        try {
            CriteriaQuery<Reader> q = cb.createQuery(Reader.class);
            Root<Reader> c = q.from(Reader.class);
            ParameterExpression<String> p = cb.parameter(String.class);
            q.select(c).where(cb.equal(c.get("username"), p));

            TypedQuery<Reader> query = em.createQuery(q);
            query.setParameter(p, username);
            reader = query.getSingleResult();
        } catch(Exception exc) {}
        return reader;
    }

    public void refreshReader(Reader reader) {
        em.refresh(reader);
    }

    public synchronized Reader createReader(String username, String password) {
        em.getTransaction().begin();

        Reader reader = new Reader(username, password);
        try {
            em.persist(reader);
            em.getTransaction().commit();
        } catch(Exception exc) {
            exc.printStackTrace();
            em.getTransaction().rollback();
            return null;
        }

        return reader;
    }

    public List<Content> getNotReaderContents(Reader reader) {
        Query q = em.createQuery("SELECT c FROM Content c" +
                " WHERE c NOT IN (SELECT cc FROM Reader r JOIN r.contents cc WHERE r.id =" + reader.getId() + ")");
        return (List<Content>) q.getResultList();
    }

    public void saveReader(Reader reader) {
        em.getTransaction().begin();
        em.merge(reader);
        em.getTransaction().commit();
    }
}
