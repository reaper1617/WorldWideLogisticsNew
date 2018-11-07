package com.gerasimchuk.repositories;

import com.gerasimchuk.entities.Admin;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Implementation of {@link AdminRepository} interface
 *
 * @author Reaper
 * @version 1.0
 */
@Repository
@Transactional
public class AdminRepositoryImpl implements AdminRepository {

    private SessionFactory sessionFactory;
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(AdminRepositoryImpl.class);

    /**
     * Instantiates a new Admin repository.
     *
     * @param sessionFactory the session factory
     */
    @Autowired
    public AdminRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public Admin create() {
        LOGGER.info("Class: " + this.getClass().getName() + " method: create");
        Admin admin = new Admin();
        sessionFactory.getCurrentSession().persist(admin);
        LOGGER.info("Persisted user: admin");
        return admin;
    }

    @Transactional
    public Admin update(int id) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: update");
        Admin updated = sessionFactory.getCurrentSession().get(Admin.class,id);
        // update actions
        sessionFactory.getCurrentSession().update(updated);
        LOGGER.info("Updated user: admin, id = " + updated.getId());
        return updated;
    }

    @Transactional
    public Admin getById(int id) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getById");
        Admin res = sessionFactory.getCurrentSession().get(Admin.class,id);
        LOGGER.info("Found user: admin, id = " + res.getId());
        return res;
    }

    @Transactional
    public Collection<Admin> getAll() {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getAll");
        Collection<Admin> res = sessionFactory.getCurrentSession().createQuery("from Admins", Admin.class).getResultList();
        LOGGER.info("Found collection: " + res + ", size = " + res.size());
        return res;
    }

    @Transactional
    public void remove(int id) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: remove");
        Admin removed = sessionFactory.getCurrentSession().get(Admin.class,id);
        sessionFactory.getCurrentSession().remove(removed);
        LOGGER.info("Removed user: admin, id = " + removed.getId());
    }
}
