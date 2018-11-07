package com.gerasimchuk.repositories;

import com.gerasimchuk.entities.City;
import com.gerasimchuk.entities.Driver;
import com.gerasimchuk.entities.Order;
import com.gerasimchuk.entities.Truck;
import com.gerasimchuk.enums.TruckState;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link TruckRepository} interface
 *
 * @author Reaper
 * @version 1.0
 */
@Repository
@Transactional
public class TruckRepositoryImpl implements TruckRepository {

    private SessionFactory sessionFactory;
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(TruckRepositoryImpl.class);

    /**
     * Instantiates a new Truck repository.
     *
     * @param sessionFactory the session factory
     */
    @Autowired
    public TruckRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public Truck create(String registrationNumber, int numOfDrivers, double capacity, TruckState state, City currentCity) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: create");
        Truck truck = new Truck(registrationNumber,numOfDrivers,capacity,state,currentCity);
        sessionFactory.getCurrentSession().persist(truck);
        LOGGER.info("Persisted truck: " + truck.getRegistrationNumber());
        return truck;
    }

    @Transactional
    public Truck update(int id, String registrationNumber, int numOfDrivers, double capacity, TruckState state, City currentCity) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: update");
        Truck updated = sessionFactory.getCurrentSession().get(Truck.class,id);
        updated.setRegistrationNumber(registrationNumber);
        updated.setNumOfDrivers(numOfDrivers);
        updated.setCapacity(capacity);
        updated.setState(state);
        updated.setCurrentCity(currentCity);
        sessionFactory.getCurrentSession().update(updated);
        LOGGER.info("Updated truck: " + updated.getRegistrationNumber());
        return updated;
    }

    @Transactional
    public Truck getById(int id) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getById");
        Truck res = sessionFactory.getCurrentSession().get(Truck.class,id);
        LOGGER.info("Found truck: " + res.getRegistrationNumber());
        return res;
    }

    @Transactional
    public Truck getByRegistrationNumber(String registrationNumber) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getByRegistrationNumber");
        if (registrationNumber==null) return null;
        Collection<Truck> trucks = sessionFactory.getCurrentSession().createQuery("from Trucks", Truck.class).getResultList();
        for(Truck t: trucks){
            if (t.getRegistrationNumber().equals(registrationNumber)){
                LOGGER.info("Found truck: id = " + t.getId() + ", registration number = " + t.getRegistrationNumber());
                return t;
            }
        }
        LOGGER.info("Truck not found");
        return null;
    }

    @Transactional
    public Collection<Truck> getAll() {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getAll");
        Collection<Truck> res = sessionFactory.getCurrentSession().createQuery("from Trucks", Truck.class).getResultList();
        LOGGER.info("Found collection: " + res + ", size = " + res.size());
        return res;
    }

    @Transactional
    public void remove(int id) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: remove");
        Truck removed = sessionFactory.getCurrentSession().get(Truck.class,id);
        sessionFactory.getCurrentSession().remove(removed);
        LOGGER.info("Removed truck: " + removed.getRegistrationNumber());
    }


    @Override
    @Transactional
    public int getNumberOfTrucksTotal() {
        //return (Integer) sessionFactory.getCurrentSession().createQuery("SELECT count (ALL)  from Trucks").uniqueResult();
        //return getAll().size();
        Number number = (Number) sessionFactory.getCurrentSession().createCriteria(Truck.class).setProjection(Projections.rowCount()).uniqueResult();
        return number.intValue();
    }

    @Override
    @Transactional
    public int getNumberOfTrucksFree() {
        // todo: make right query instead!
      //return sessionFactory.getCurrentSession().createQuery("from Trucks t where t.assignedOrder = null").getResultList().size();
        Collection<Truck> allTrucks = getAll();
        int res = 0;
        for(Truck t: allTrucks){
            if (t.getAssignedOrder() == null && t.getState().equals(TruckState.READY)) res ++;
        }
        return res;
    }

    @Override
    @Transactional
    public int getNumberOfTrucksNotReady() {
        // todo: make right query instead!
        Collection<Truck> allTrucks = getAll();
        int res = 0;
        for(Truck t: allTrucks){
            if (t.getState().equals(TruckState.NOT_READY)) res++ ;
        }
        return res;
    }

    @Override
    @Transactional
    public int getNumberOfTrucksExecutingOrder() {
        // todo: make right query instead!
        Collection<Truck> allTrucks = getAll();
        int res = 0;
        for(Truck t: allTrucks){
            if (t.getAssignedOrder() != null) res++;
        }
        return res;
    }

    @Override
    @Transactional
    public Collection<Truck> getTrucksForOnePage(int pageSize, int pageNumber) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getTrucksForOnePage()");
        if (pageSize == 0) {
            LOGGER.error("Class: " + this.getClass().getName() + " method: getTrucksForOnePage() error: pageSize = 0");
            return null;
        }
        int recordsNum = ((Long)sessionFactory.getCurrentSession().createQuery("select count(*) from Trucks").uniqueResult()).intValue();
        LOGGER.info("Class: " + this.getClass().getName() + " method: getTrucksForOnePage(), recordsNum = " + recordsNum);
        int pagesNum = recordsNum/pageSize;
        if (recordsNum%pageSize !=0) pagesNum +=1;
        LOGGER.info("Class: " + this.getClass().getName() + " method: getTrucksForOnePage(), pagesNum = " + pagesNum);
        if (pageNumber > pagesNum-1){
            LOGGER.error("Class: " + this.getClass().getName() + " method: getTrucksForOnePage() error: page number > total number of pages.");
            return null;
        }
        String query = "select * from Trucks desc " + pageSize ;
        //String testQuery = "select * from Orders limit 0,2"; /// + pageNumber*pageSize + "," + pageSize ;
        Query q = sessionFactory.getCurrentSession().createQuery("from Trucks");
        q.setFirstResult(pageNumber*pageSize);
        q.setMaxResults(pageSize);
        List<Truck> res = (List<Truck>)q.list();
        return res;
    }
}
