package com.gerasimchuk.repositories;

import com.gerasimchuk.entities.City;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * Implementation of {@link CityRepository} interface
 *
 * @author Reaper
 * @version 1.0
 */
@Repository
@Transactional
public class CityRepositoryImpl implements CityRepository {

    private SessionFactory sessionFactory;
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(CityRepositoryImpl.class);

    /**
     * Instantiates a new City repository.
     *
     * @param sessionFactory the session factory
     */
    @Autowired
    public CityRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public City create(String name, boolean hasAgency) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: create");
        City city = new City(name,hasAgency);
        sessionFactory.getCurrentSession().persist(city);
        LOGGER.info("Persisted city: " + city.getName());
        return city;
    }

    @Transactional
    public City update(int id, String name, boolean hasAgency) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: update");
        City updated = sessionFactory.getCurrentSession().get(City.class,id);
        updated.setName(name);
        updated.setHasAgency(hasAgency);
        sessionFactory.getCurrentSession().update(updated);
        LOGGER.info("Updated city: " + updated.getName());
        return updated;
    }

    @Transactional
    public City getById(int id) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getById");
        City res = sessionFactory.getCurrentSession().get(City.class,id);
        LOGGER.info("Found city: " + res.getName());
        return res;
    }

    @Transactional
    public City getByName(String name) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getByName");
        Collection<City> cities = sessionFactory.getCurrentSession().createQuery("from Cities", City.class).getResultList();
        for(City c: cities){
            if (c.getName().equals(name)){
                LOGGER.info("Found city: " + c.getName());
                return c;
            }
        }
        LOGGER.info("City not found");
        return null;
    }

    @Transactional
    public Collection<City> getAll() {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getAll");
        Collection<City> res = sessionFactory.getCurrentSession().createQuery("from Cities", City.class).getResultList();
        LOGGER.info("Found collection: " + res + ", size = " + res.size());
        return res;
    }

    @Transactional
    public void remove(int id) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: remove");
        City removed = sessionFactory.getCurrentSession().get(City.class, id);
        sessionFactory.getCurrentSession().remove(removed);
        LOGGER.info("Removed city: " + removed.getName());
    }

    @Override
    @Transactional
    public Collection<City> getCitiesForOnePage(int pageSize, int pageNumber) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getCitiesForOnePage()");
        if (pageSize == 0) {
            LOGGER.error("Class: " + this.getClass().getName() + " method: getCitiesForOnePage() error: pageSize = 0");
            return null;
        }
        int recordsNum = ((Long)sessionFactory.getCurrentSession().createQuery("select count(*) from Cities").uniqueResult()).intValue();
        LOGGER.info("Class: " + this.getClass().getName() + " method: getCitiesForOnePage(), recordsNum = " + recordsNum);
        int pagesNum = recordsNum/pageSize;
        if (recordsNum%pageSize !=0) pagesNum +=1;
        LOGGER.info("Class: " + this.getClass().getName() + " method: getCitiesForOnePage(), pagesNum = " + pagesNum);
        if (pageNumber > pagesNum-1){
            LOGGER.error("Class: " + this.getClass().getName() + " method: getCitiesForOnePage() error: page number > total number of pages.");
            return null;
        }
        String query = "select * from Cities desc " + pageSize;
        //String testQuery = "select * from Orders limit 0,2"; /// + pageNumber*pageSize + "," + pageSize ;
        Query q = sessionFactory.getCurrentSession().createQuery("from Cities");
        q.setFirstResult(pageNumber*pageSize);
        q.setMaxResults(pageSize);
        List<City> res = (List<City>)q.list();
        return res;

    }
}
