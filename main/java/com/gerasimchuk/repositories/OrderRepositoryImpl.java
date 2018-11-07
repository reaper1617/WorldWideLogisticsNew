package com.gerasimchuk.repositories;

import com.gerasimchuk.entities.Order;
import com.gerasimchuk.entities.Truck;
import com.gerasimchuk.enums.OrderStatus;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.List;

/**
 * Implementation of {@link OrderRepository} interface
 *
 * @author Reaper
 * @version 1.0
 */
@Repository
@Transactional
public class OrderRepositoryImpl implements OrderRepository {

    private SessionFactory sessionFactory;
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(OrderRepositoryImpl.class);

    /**
     * Instantiates a new Order repository.
     *
     * @param sessionFactory the session factory
     */
    @Autowired
    public OrderRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public Order create(String personalNumber, String description, String date, OrderStatus status, Truck assignedTruck) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: create");
        Order order = new Order(personalNumber, description, date, status, assignedTruck);
        sessionFactory.getCurrentSession().persist(order);
        LOGGER.info("Persisted order: " + order.getDescription());
        return order;
    }

    @Transactional
    public Order update(int id, String personalNumber, String description, String date, OrderStatus status, Truck assignedTruck) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: update");
        Order updated = sessionFactory.getCurrentSession().get(Order.class,id);
        updated.setPersonalNumber(personalNumber);
        updated.setDescription(description);
        updated.setDate(date);
        updated.setStatus(status);
        updated.setAssignedTruck(assignedTruck);
        sessionFactory.getCurrentSession().update(updated);
        LOGGER.info("Updated order: " + updated.getDescription());
        return updated;
    }

    @Transactional
    public Order getById(int id) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getById");
        Order res = sessionFactory.getCurrentSession().get(Order.class, id);
        LOGGER.info("Found order: " + res.getDescription());
        return res;
    }

    @Override
    public Order getByPersonalNumber(String personalNumber) {
        Collection<Order> allOrders = getAll();
        for(Order o: allOrders){
            if (o.getPersonalNumber().equals(personalNumber)) return o;
        }
        return null;
    }

    @Transactional
    public Collection<Order> getAll() {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getAll");
        Collection<Order> res = sessionFactory.getCurrentSession().createQuery("from Orders", Order.class).getResultList();
        LOGGER.info("Found collection: " + res + ", size = " + res.size());
        return res;
    }

    @Override
    @Transactional
    public Collection<Order> getOrdersForOnePage(int pageSize, int pageNumber) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getOrdersForOnePage()");
        if (pageSize == 0) {
            LOGGER.error("Class: " + this.getClass().getName() + " method: getOrdersForOnePage() error: pageSize = 0");
            return null;
        }
        int recordsNum = ((Long)sessionFactory.getCurrentSession().createQuery("select count(*) from Orders").uniqueResult()).intValue();
        LOGGER.info("Class: " + this.getClass().getName() + " method: getOrdersForOnePage(), recordsNum = " + recordsNum);
        int pagesNum = recordsNum/pageSize;
        if (recordsNum%pageSize !=0) pagesNum +=1;
        LOGGER.info("Class: " + this.getClass().getName() + " method: getOrdersForOnePage(), pagesNum = " + pagesNum);
        if (pageNumber > pagesNum-1){
            LOGGER.error("Class: " + this.getClass().getName() + " method: getOrdersForOnePage() error: page number > total number of pages.");
            return null;
        }
        String query = "select * from Orders desc " + pageSize ;
        //String testQuery = "select * from Orders limit 0,2"; /// + pageNumber*pageSize + "," + pageSize ;
        Query q = sessionFactory.getCurrentSession().createQuery("from Orders");
        q.setFirstResult(pageNumber*pageSize);
        q.setMaxResults(pageSize);
            List<Order> res = (List<Order>)q.list();
            return res;
//        LOGGER.info("Class: " + this.getClass().getName() + " method: getOrdersForOnePage(), Query = " + query );
//        List<Order> resultList = sessionFactory.getCurrentSession().createQuery(q)
//        LOGGER.info("Class: " + this.getClass().getName() + " method: getOrdersForOnePage(), resultList = " + resultList);
//        LOGGER.info("Class: " + this.getClass().getName() + " out from getOrdersForOnePage()");
//        return resultList;
    }

    @Override
    @Transactional
    public Collection<Order> getTopNonExecutedOrders(int size) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getTopNonExecutedOrders()");
        if (size <= 0) {
            LOGGER.info("Class: " + this.getClass().getName() + " out from getTopNonExecutedOrders() method: requested size less or equals 0");
            return null;
        }
        //String query = "select * from Orders where status != 'EXECUTED' limit " + size;
        //String query = "select * from orders o where o.status != 'EXECUTED' limit " + size;
        String query = "from Orders where status!='EXECUTED'";
        Query q = sessionFactory.getCurrentSession().createQuery(query);
        q.setMaxResults(size);
        List<Order> res =(List<Order>)(q.list());
        LOGGER.info("Class: " + this.getClass().getName() + " out from getTopNonExecutedOrders(), result orders collection: " + res);
        return res;
    }

    @Override
    @Transactional
    public Collection<Order> getLastOrders(int numberOfOrders) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: getLastOrders()");
        if (numberOfOrders <= 0) {
            LOGGER.info("Class: " + this.getClass().getName() + " out from getLastOrders() method: requested number of orders less or equals 0");
            return null;
        }
        //String query = "select * from Orders where status != 'EXECUTED' limit " + size;
        //String query = "select * from orders o where o.status != 'EXECUTED' limit " + size;
        String query = "from Orders o order by o.id desc"; // todo: desc
        Query q = sessionFactory.getCurrentSession().createQuery(query);
        q.setMaxResults(numberOfOrders);
        List<Order> res =(List<Order>)(q.list());
        LOGGER.info("Class: " + this.getClass().getName() + " out from getLastOrders(), result orders collection: " + res);
        return res;
    }

    @Transactional
    public void remove(int id) {
        LOGGER.info("Class: " + this.getClass().getName() + " method: remove");
        Order removed = sessionFactory.getCurrentSession().get(Order.class, id);
        sessionFactory.getCurrentSession().remove(removed);
        LOGGER.info("Removed order: " + removed.getDescription());
    }
}
