package com.gerasimchuk.repositories;

import com.gerasimchuk.entities.Cargo;
import com.gerasimchuk.entities.Order;
import com.gerasimchuk.entities.Route;
import com.gerasimchuk.enums.CargoStatus;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * Implementation of {@link CargoRepository} interface
 *
 * @author Reaper
 * @version 1.0
 */
@Repository
@Transactional
public class CargoRepositoryImpl implements CargoRepository {

    private SessionFactory sessionFactory;
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(CargoRepositoryImpl.class);

    /**
     * Instantiates a new Cargo repository.
     *
     * @param sessionFactory the session factory
     */
    @Autowired
    public CargoRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public Cargo create(String personalNumber, String name, double weight, CargoStatus status, Route route) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: create");
        Cargo cargo = new Cargo(personalNumber,name,weight,status,route);
        sessionFactory.getCurrentSession().persist(cargo);
        LOGGER.info("Persisted cargo: " + cargo.getName());
        return cargo;
    }

    @Transactional
    public Cargo update(int id, String personalNumber, String name, double weight, CargoStatus status, Route route) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: update");
        Cargo updated = sessionFactory.getCurrentSession().get(Cargo.class, id);
        updated.setPersonalNumber(personalNumber);
        updated.setName(name);
        updated.setWeight(weight);
        updated.setStatus(status);
        updated.setRoute(route);
        sessionFactory.getCurrentSession().update(updated);
        LOGGER.info("Updated cargo: " + updated.getName());
        return updated;
    }

    @Transactional
    public Cargo update(int id, String personalNumber, String name, double weight, CargoStatus status, Route route, Order order) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: update");
        Cargo updated = sessionFactory.getCurrentSession().get(Cargo.class, id);
        updated.setPersonalNumber(personalNumber);
        updated.setName(name);
        updated.setWeight(weight);
        updated.setStatus(status);
        updated.setRoute(route);
        updated.setOrder(order);
        sessionFactory.getCurrentSession().update(updated);
        LOGGER.info("Updated cargo: " + updated.getName());
        return updated;
    }

    @Transactional
    public Cargo getById(int id) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getById");
        Cargo res = sessionFactory.getCurrentSession().get(Cargo.class, id);
        LOGGER.info("Found cargo: " + res.getName() + ", id = " + res.getId());
        return res;
    }

    @Override
    @Transactional
    public Cargo getByName(String name) {
        Collection<Cargo> cargos = sessionFactory.getCurrentSession().createQuery("from Cargos", Cargo.class).getResultList();
        for(Cargo c: cargos){
            if (c.getName().equals(name)) return c;
        }
        return null;
    }

    @Transactional
    public Collection<Cargo> getAll() {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getAll");
        Collection<Cargo> res = sessionFactory.getCurrentSession().createQuery("from Cargos", Cargo.class).getResultList();
        LOGGER.info("Found collection: " + res + ", size = " + res.size());
        return res;
    }

    @Transactional
    public void remove(int id) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: remove");
        Cargo removed = sessionFactory.getCurrentSession().get(Cargo.class,id);
        sessionFactory.getCurrentSession().remove(removed);
        LOGGER.info("Removed cargo: " + removed.getName());
    }

    @Override
    @Transactional
    public Collection<Cargo> getCargosForOnePage(int pageSize, int pageNumber) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getCargosForOnePage()");
        if (pageSize == 0) {
            LOGGER.error("Class: " + this.getClass().getName() + " method: getCargosForOnePage() error: pageSize = 0");
            return null;
        }
        int recordsNum = ((Long)sessionFactory.getCurrentSession().createQuery("select count(*) from Cargos").uniqueResult()).intValue();
        LOGGER.info("Class: " + this.getClass().getName() + " method: getCargosForOnePage(), recordsNum = " + recordsNum);
        int pagesNum = recordsNum/pageSize;
        if (recordsNum%pageSize !=0) pagesNum +=1;
        LOGGER.info("Class: " + this.getClass().getName() + " method: getCargosForOnePage(), pagesNum = " + pagesNum);
        if (pageNumber > pagesNum-1){
            LOGGER.error("Class: " + this.getClass().getName() + " method: getCargosForOnePage() error: page number > total number of pages.");
            return null;
        }
        String query = "select * from Cargos desc " + pageSize ;
        //String testQuery = "select * from Orders limit 0,2"; /// + pageNumber*pageSize + "," + pageSize ;
        Query q = sessionFactory.getCurrentSession().createQuery("from Cargos");
        q.setFirstResult(pageNumber*pageSize);
        q.setMaxResults(pageSize);
        List<Cargo> res = (List<Cargo>)q.list();
        return res;
    }
}
