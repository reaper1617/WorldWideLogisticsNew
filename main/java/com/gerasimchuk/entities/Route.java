package com.gerasimchuk.entities;

import javax.persistence.*;
import java.util.Set;

/**
 * The type Route.
 */
@Entity(name = "Routes")
@Table(name = "routes", schema = "mywwldatabase", catalog = "")
public class Route {
    private int id;
    private City cityFrom;
    private City cityTo;
    private double distance;
    private Set<Cargo> cargosOnRoute;

    /**
     * Instantiates a new Route.
     */
    public Route() {
    }

    /**
     * Instantiates a new Route.
     *
     * @param cityFrom the city from
     * @param cityTo   the city to
     * @param distance the distance
     */
    public Route(City cityFrom, City cityTo, double distance) {
        this.cityFrom = cityFrom;
        this.cityTo = cityTo;
        this.distance = distance;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets city from.
     *
     * @return the city from
     */
    @ManyToOne
    @JoinColumn(name = "city_from_id", nullable = false)
    public City getCityFrom() {
        return cityFrom;
    }

    /**
     * Sets city from.
     *
     * @param cityFrom the city from
     */
    public void setCityFrom(City cityFrom) {
        this.cityFrom = cityFrom;
    }

    /**
     * Gets city to.
     *
     * @return the city to
     */
    @ManyToOne
    @JoinColumn(name = "city_to_id", nullable = false)
    public City getCityTo() {
        return cityTo;
    }

    /**
     * Sets city to.
     *
     * @param cityTo the city to
     */
    public void setCityTo(City cityTo) {
        this.cityTo = cityTo;
    }

    /**
     * Gets distance.
     *
     * @return the distance
     */
    @Column(name = "distance", nullable = false)
    public double getDistance() {
        return distance;
    }

    /**
     * Sets distance.
     *
     * @param distance the distance
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * Gets cargos on route.
     *
     * @return the cargos on route
     */
    @OneToMany(mappedBy = "route", fetch = FetchType.EAGER)
    public Set<Cargo> getCargosOnRoute() {
        return cargosOnRoute;
    }

    /**
     * Sets cargos on route.
     *
     * @param cargosOnRoute the cargos on route
     */
    public void setCargosOnRoute(Set<Cargo> cargosOnRoute) {
        this.cargosOnRoute = cargosOnRoute;
    }

}
