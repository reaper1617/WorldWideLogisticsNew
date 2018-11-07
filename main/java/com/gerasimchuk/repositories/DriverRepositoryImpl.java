package com.gerasimchuk.repositories;

import com.gerasimchuk.entities.City;
import com.gerasimchuk.entities.Driver;
import com.gerasimchuk.entities.Truck;
import com.gerasimchuk.enums.DriverStatus;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Implementation of {@link DriverRepository} interface
 *
 * @author Reaper
 * @version 1.0
 */
@Repository
@Transactional
public class DriverRepositoryImpl implements DriverRepository {

    private SessionFactory sessionFactory;
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(DriverRepositoryImpl.class);

    /**
     * Instantiates a new Driver repository.
     *
     * @param sessionFactory the session factory
     */
    @Autowired
    public DriverRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public Driver create(double hoursWorked, DriverStatus status, City currentCity, Truck currentTruck) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: create");
        Driver driver = new Driver(hoursWorked,status,currentCity,currentTruck);
        sessionFactory.getCurrentSession().persist(driver);
        LOGGER.info("Persisted user: driver");
        return driver;
    }

    @Transactional
    public Driver update(int id, double hoursWorked, DriverStatus status, City currentCity, Truck currentTruck) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: update");
        Driver updated = sessionFactory.getCurrentSession().get(Driver.class,id);
        updated.setHoursWorked(hoursWorked);
        updated.setStatus(status);
        updated.setCurrentCity(currentCity);
        updated.setCurrentTruck(currentTruck);
        sessionFactory.getCurrentSession().update(updated);
        LOGGER.info("Updated user: driver, id = " + updated.getId());
        return updated;
    }

    @Transactional
    public Driver getById(int id){
        LOGGER.info("Class: " + this.getClass().getName() + " method: getById");
        Driver res = sessionFactory.getCurrentSession().get(Driver.class, id);
        LOGGER.info("Found driver: id =  " + res.getId()
                + " hours worked = " + res.getHoursWorked()
                + ", status = " + res.getStatus()
                + ", current city = " + res.getCurrentCity().getName()
                + " current truck = " + res.getCurrentTruck());
        return res;
    }

    @Transactional
    public Collection<Driver> getAll() {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getAll");
        Collection<Driver> res = sessionFactory.getCurrentSession().createQuery("from Drivers", Driver.class).getResultList();
        LOGGER.info("Found collection: " + res + ", size = " + res.size());
        return res;
    }

    public void remove(int id) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: remove");
        Driver removed = sessionFactory.getCurrentSession().get(Driver.class,id);
        sessionFactory.getCurrentSession().remove(removed);
        LOGGER.info("Removed driver: id = " + removed.getId());
    }

    @Override
    @Transactional
    public int getNumOfDriversTotal() {
        return getAll().size();
    }

    @Override
    @Transactional
    public int getNumOfDriversExecutingOrders() {
        Collection<Driver> allDrivers = getAll();
        int res = 0;
        for(Driver d: allDrivers){
            if (d.getCurrentTruck()!=null){
                if (d.getCurrentTruck().getAssignedOrder()!=null) res++;
            }
        }
        return res;
    }

    @Override
    @Transactional
    public int getNumOfDriversFree() {
        // todo: make hql !!
        Collection<Driver> allDrivers = getAll();
        int res = 0;
        for(Driver d: allDrivers){
            if (d.getStatus().equals(DriverStatus.FREE)) res++;
        }
        return res;
    }
}
