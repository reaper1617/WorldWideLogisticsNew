package com.gerasimchuk.repositories;

import com.gerasimchuk.entities.Admin;
import com.gerasimchuk.entities.Driver;
import com.gerasimchuk.entities.Manager;
import com.gerasimchuk.entities.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * Implementation of {@link UserRepository} interface
 *
 * @author Reaper
 * @version 1.0
 */
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    private SessionFactory sessionFactory;
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(UserRepositoryImpl.class);

    /**
     * Instantiates a new User repository.
     *
     * @param sessionFactory the session factory
     */
    @Autowired
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public User create(String name, String middleName, String lastName, String personalNumber, String password, Driver driver, Manager manager, Admin admin) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: create");
        User user = new User(name,middleName,lastName,personalNumber,password,driver,manager,admin);
        sessionFactory.getCurrentSession().persist(user);
        LOGGER.info("Persisted user: " + user.getPersonalNumber());
        return user;
    }

    @Transactional
    public User update(int id, String name, String middleName, String lastName, String personalNumber, String password, Driver driver, Manager manager, Admin admin) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: update");
        User updated = sessionFactory.getCurrentSession().get(User.class,id);
        updated.setName(name);
        updated.setMiddleName(middleName);
        updated.setLastName(lastName);
        updated.setPersonalNumber(personalNumber);
        updated.setPassword(password);
        updated.setDriver(driver);
        updated.setManager(manager);
        updated.setAdmin(admin);
        sessionFactory.getCurrentSession().update(updated);
        LOGGER.info("Updated user: id = " + updated.getId() + ", personal number = " + updated.getPersonalNumber());
        return updated;
    }

    @Transactional
    public User getById(int id) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getById");
        User res = sessionFactory.getCurrentSession().get(User.class,id);
        LOGGER.info("Found user: " + res.getPersonalNumber());
        return res;
    }

    @Transactional
    public User getByDriverId(int driverId) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getByDriverId");
        Collection<User> users = sessionFactory.getCurrentSession().createQuery("from Users", User.class).getResultList();
        for(User u: users){
            if (u.getDriver()!=null){
                if (u.getDriver().getId() == driverId){
                    LOGGER.info("Found user: driver, driverId = " + u.getId());
                    return u;
                }
            }
        }
        LOGGER.info("User not found");
        return null;
    }

    public User getByManagerId(int managerId) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getByManagerId");
        Collection<User> users = sessionFactory.getCurrentSession().createQuery("from Users", User.class).getResultList();
        for(User u: users){
            if (u.getManager()!=null){
                if (u.getManager().getId()==managerId) {
                    LOGGER.info("Found user: manager, managerId = " + u.getId());
                    return u;
                }
            }
        }
        LOGGER.info("User not found");
        return null;
    }

    public User getByAdminId(int adminId) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getByAdminId");
        Collection<User> users = sessionFactory.getCurrentSession().createQuery("from Users", User.class).getResultList();
        for(User u: users){
            if (u.getAdmin()!=null){
                LOGGER.info("Found user: admin, adminId = " + u.getId());
                if (u.getAdmin().getId()==adminId) return u;
            }
        }
        LOGGER.info("User not found");
        return null;
    }

    @Transactional
    public User getByPersonalNumber(String personalNumber) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getByPersonalNumber");
        Collection<User> users = sessionFactory.getCurrentSession().createQuery("from Users", User.class).getResultList();
        for(User u: users){
            if (u.getPersonalNumber().equals(personalNumber)){
                LOGGER.info("Found user: personal number = " + u.getPersonalNumber());
                return u;
            }
        }
        LOGGER.info("User not found");
        return null;
    }

    @Transactional
    public Collection<User> getAll() {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getAll");
        Collection<User> res = sessionFactory.getCurrentSession().createQuery("from Users", User.class).getResultList();
        LOGGER.info("Found collection: " + res + ", size = " + res.size());
        return res;
    }

    @Transactional
    public void remove(int id) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: remove");
        User removed = sessionFactory.getCurrentSession().get(User.class,id);
        sessionFactory.getCurrentSession().remove(removed);
        LOGGER.info("Removed user: id = " + removed.getId() + ", personal number = " + removed.getPersonalNumber());
    }

    @Override
    @Transactional
    public Collection<User> getUsersForOnePage(int pageSize, int pageNumber) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getUsersForOnePage()");
        if (pageSize == 0) {
            LOGGER.error("Class: " + this.getClass().getName() + " method: getUsersForOnePage() error: pageSize = 0");
            return null;
        }
        int recordsNum = ((Long)sessionFactory.getCurrentSession().createQuery("select count(*) from Users").uniqueResult()).intValue();
        LOGGER.info("Class: " + this.getClass().getName() + " method: getUsersForOnePage(), recordsNum = " + recordsNum);
        int pagesNum = recordsNum/pageSize;
        if (recordsNum%pageSize !=0) pagesNum +=1;
        LOGGER.info("Class: " + this.getClass().getName() + " method: getUsersForOnePage(), pagesNum = " + pagesNum);
        if (pageNumber > pagesNum-1){
            LOGGER.error("Class: " + this.getClass().getName() + " method: getUsersForOnePage() error: page number > total number of pages.");
            return null;
        }
        String query = "select * from Trucks desc " + pageSize ;
        //String testQuery = "select * from Orders limit 0,2"; /// + pageNumber*pageSize + "," + pageSize ;
        Query q = sessionFactory.getCurrentSession().createQuery("from Users");
        q.setFirstResult(pageNumber*pageSize);
        q.setMaxResults(pageSize);
        List<User> res = (List<User>)q.list();
        return res;
    }
}
