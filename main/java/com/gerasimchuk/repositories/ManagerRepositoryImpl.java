package com.gerasimchuk.repositories;

import com.gerasimchuk.entities.Manager;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Implementation of {@link ManagerRepository} interface
 *
 * @author Reaper
 * @version 1.0
 */
@Repository
@Transactional
public class ManagerRepositoryImpl implements ManagerRepository {


    private SessionFactory sessionFactory;
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(ManagerRepositoryImpl.class);

    /**
     * Instantiates a new Manager repository.
     *
     * @param sessionFactory the session factory
     */
    @Autowired
    public ManagerRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public Manager create() {
        LOGGER.info("Class: " + this.getClass().getName() + " method: create");
        Manager manager = new Manager();
        sessionFactory.getCurrentSession().persist(manager);
        LOGGER.info("Persisted user: manager");
        return manager;
    }

    @Transactional
    public Manager update(int id) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: update");
        Manager updated = sessionFactory.getCurrentSession().get(Manager.class,id);
        // update actions
        sessionFactory.getCurrentSession().update(updated);
        LOGGER.info("Updated user: manager");
        return updated;
    }

    @Transactional
    public Manager getById(int id) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getById");
        Manager res = sessionFactory.getCurrentSession().get(Manager.class, id);
        LOGGER.info("Found manager: id =  " + res.getId());
        return res;
    }

    @Transactional
    public Collection<Manager> getAll() {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getAll");
        Collection<Manager> res = sessionFactory.getCurrentSession().createQuery("from Managers",Manager.class).getResultList();
        LOGGER.info("Found collection: " + res + ", size = " + res.size());
        return res;
    }

    public void remove(int id) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: remove");
        Manager removed = sessionFactory.getCurrentSession().get(Manager.class, id);
        sessionFactory.getCurrentSession().remove(removed);
        LOGGER.info("Removed manager: id = " + removed.getId());
    }
}
