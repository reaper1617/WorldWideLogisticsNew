package com.gerasimchuk.repositories;

import com.gerasimchuk.entities.City;
import com.gerasimchuk.entities.Route;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * Implementation of {@link RouteRepository} interface
 *
 * @author Reaper
 * @version 1.0
 */
@Repository
@Transactional
public class RouteRepositoryImpl implements RouteRepository {

    private SessionFactory sessionFactory;
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(RouteRepositoryImpl.class);

    /**
     * Instantiates a new Route repository.
     *
     * @param sessionFactory the session factory
     */
    @Autowired
    public RouteRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public Route create(City cityFrom, City cityTo, double distance) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: create");
        Route route = new Route(cityFrom,cityTo,distance);
        sessionFactory.getCurrentSession().persist(route);
        LOGGER.info("Persisted route: from " + route.getCityFrom().getName() + " to" + route.getCityTo().getName() + ", distance = " + route.getDistance());
        return route;
    }

    @Transactional
    public Route update(int id, City cityFrom, City cityTo, double distance) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: update");
        Route updated = sessionFactory.getCurrentSession().get(Route.class,id);
        updated.setCityFrom(cityFrom);
        updated.setCityTo(cityTo);
        updated.setDistance(distance);
        sessionFactory.getCurrentSession().update(updated);
        LOGGER.info("Updated route: id = " + updated.getId() +  ", from " + updated.getCityFrom().getName() + " to" + updated.getCityTo().getName() + ", distance = " + updated.getDistance());
        return updated;
    }

    @Transactional
    public Route getById(int id) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getById");
        Route res = sessionFactory.getCurrentSession().get(Route.class, id);
        LOGGER.info("Found route: id = " + res.getId()
                + ", from " + res.getCityFrom().getName()
                + " to" + res.getCityTo().getName()
                + ", distance = " + res.getDistance());
        return res;
    }

    @Transactional
    public Route getByCities(City cityFrom, City cityTo) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getByCities");
        Collection<Route> routes = sessionFactory.getCurrentSession().createQuery("from Routes", Route.class).getResultList();
        for(Route r: routes){
            if (r.getCityFrom().getName().equals(cityFrom.getName()) && r.getCityTo().getName().equals(cityTo.getName())){
                LOGGER.info("Found route: id = " + r.getId()
                        + ", from " + r.getCityFrom().getName()
                        + " to" + r.getCityTo().getName()
                        + ", distance = " + r.getDistance());
                return r;
            }
        }
        LOGGER.info("Route not found");
        return null;
    }

    public Collection<Route> getAll() {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getAll");
        Collection<Route> res = sessionFactory.getCurrentSession().createQuery("from Routes", Route.class).getResultList();
        LOGGER.info("Found collection: " + res + ", size = " + res.size());
        return res;
    }

    @Transactional
    public void remove(int id) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: remove");
        Route removed = sessionFactory.getCurrentSession().get(Route.class,id);
        sessionFactory.getCurrentSession().remove(removed);
        LOGGER.info("URemoved route: id = " + removed.getId() +  ", from " + removed.getCityFrom().getName() + " to" + removed.getCityTo().getName() + ", distance = " + removed.getDistance());
    }

    @Override
    @Transactional
    public Collection<Route> getRoutesForOnePage(int pageSize, int pageNumber) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getRoutesForOnePage()");
        if (pageSize == 0) {
            LOGGER.error("Class: " + this.getClass().getName() + " method: getRoutesForOnePage() error: pageSize = 0");
            return null;
        }
        int recordsNum = ((Long)sessionFactory.getCurrentSession().createQuery("select count(*) from Routes").uniqueResult()).intValue();
        LOGGER.info("Class: " + this.getClass().getName() + " method: getRoutesForOnePage(), recordsNum = " + recordsNum);
        int pagesNum = recordsNum/pageSize;
        if (recordsNum%pageSize !=0) pagesNum +=1;
        LOGGER.info("Class: " + this.getClass().getName() + " method: getRoutesForOnePage(), pagesNum = " + pagesNum);
        if (pageNumber > pagesNum-1){
            LOGGER.error("Class: " + this.getClass().getName() + " method: getRoutesForOnePage() error: page number > total number of pages.");
            return null;
        }
        String query = "select * from Routes desc " + pageSize ;
        //String testQuery = "select * from Orders limit 0,2"; /// + pageNumber*pageSize + "," + pageSize ;
        Query q = sessionFactory.getCurrentSession().createQuery("from Routes");
        q.setFirstResult(pageNumber*pageSize);
        q.setMaxResults(pageSize);
        List<Route> res = (List<Route>)q.list();
        return res;
    }
}
