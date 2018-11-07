package com.gerasimchuk.entities;


import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

/**
 * The type City.
 */
@Entity(name = "Cities")
@Table(name = "cities", schema = "mywwldatabase", catalog = "")
public class City {

    private int id;
    private String name;
    private boolean hasAgency;
    private Set<Driver> driversInCity;
    private Set<City> citiesFrom;
    private Set<City> citiesTo;
    private Set<Truck> trucksInCity;

    /**
     * Instantiates a new City.
     */
    public City() {
    }

    /**
     * Instantiates a new City.
     *
     * @param name      the name
     * @param hasAgency the has agency
     */
    public City(String name, boolean hasAgency) {
        this.name = name;
        this.hasAgency = hasAgency;
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
     * Gets name.
     *
     * @return the name
     */
    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Is has agency boolean.
     *
     * @return the boolean
     */
    @Column(name = "has_agency", nullable = false)
    public boolean isHasAgency() {
        return hasAgency;
    }

    /**
     * Sets has agency.
     *
     * @param hasAgency the has agency
     */
    public void setHasAgency(boolean hasAgency) {
        this.hasAgency = hasAgency;
    }

    /**
     * Gets drivers in city.
     *
     * @return the drivers in city
     */
    @OneToMany(mappedBy = "currentCity", fetch = FetchType.EAGER)
    public Set<Driver> getDriversInCity() {
        return driversInCity;
    }

    /**
     * Sets drivers in city.
     *
     * @param driversInCity the drivers in city
     */
    public void setDriversInCity(Set<Driver> driversInCity) {
        this.driversInCity = driversInCity;
    }

    /**
     * Gets cities from.
     *
     * @return the cities from
     */
    @OneToMany(targetEntity = Route.class,mappedBy = "cityFrom", fetch = FetchType.EAGER)
    public Set<City> getCitiesFrom() {
        return citiesFrom;
    }

    /**
     * Sets cities from.
     *
     * @param citiesFrom the cities from
     */
    public void setCitiesFrom(Set<City> citiesFrom) {
        this.citiesFrom = citiesFrom;
    }

    /**
     * Gets cities to.
     *
     * @return the cities to
     */
    @OneToMany(targetEntity = Route.class, mappedBy = "cityTo", fetch = FetchType.EAGER)
    public Set<City> getCitiesTo() {
        return citiesTo;
    }

    /**
     * Sets cities to.
     *
     * @param citiesTo the cities to
     */
    public void setCitiesTo(Set<City> citiesTo) {
        this.citiesTo = citiesTo;
    }

    /**
     * Gets trucks in city.
     *
     * @return the trucks in city
     */
    @OneToMany(mappedBy = "currentCity", fetch = FetchType.EAGER)
    public Set<Truck> getTrucksInCity() {
        return trucksInCity;
    }

    /**
     * Sets trucks in city.
     *
     * @param trucksInCity the trucks in city
     */
    public void setTrucksInCity(Set<Truck> trucksInCity) {
        this.trucksInCity = trucksInCity;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (id != city.id) return false;
        if (hasAgency != city.hasAgency) return false;
        return name.equals(city.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + (hasAgency ? 1 : 0);
        return result;
    }
}
