package com.gerasimchuk.dto;

/**
 * City Data Transfer Object
 *
 * @author Reaper
 * @version 1.0
 */
public class CityDTO {
    private String id;
    private String name;
    private String hasAgency;
    private String[] driversInCity;
    private String[] trucksInCity;

    /**
     * Instantiates a new City dto.
     */
    public CityDTO() {
    }

    /**
     * Instantiates a new City dto.
     *
     * @param id        the id
     * @param name      the name
     * @param hasAgency the has agency
     */
    public CityDTO(String id, String name, String hasAgency) {
        this.id = id;
        this.name = name;
        this.hasAgency = hasAgency;
    }

    /**
     * Instantiates a new City dto.
     *
     * @param id            the id
     * @param name          the name
     * @param hasAgency     the has agency
     * @param driversInCity the drivers in city
     * @param trucksInCity  the trucks in city
     */
    public CityDTO(String id, String name, String hasAgency, String[] driversInCity, String[] trucksInCity) {
        this.id = id;
        this.name = name;
        this.hasAgency = hasAgency;
        this.driversInCity = driversInCity;
        this.trucksInCity = trucksInCity;
    }

    /**
     * Get drivers in city string [ ].
     *
     * @return the string [ ]
     */
    public String[] getDriversInCity() {
        return driversInCity;
    }

    /**
     * Sets drivers in city.
     *
     * @param driversInCity the drivers in city
     */
    public void setDriversInCity(String[] driversInCity) {
        this.driversInCity = driversInCity;
    }

    /**
     * Get trucks in city string [ ].
     *
     * @return the string [ ]
     */
    public String[] getTrucksInCity() {
        return trucksInCity;
    }

    /**
     * Sets trucks in city.
     *
     * @param trucksInCity the trucks in city
     */
    public void setTrucksInCity(String[] trucksInCity) {
        this.trucksInCity = trucksInCity;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
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
     * Gets has agency.
     *
     * @return the has agency
     */
    public String getHasAgency() {
        return hasAgency;
    }

    /**
     * Sets has agency.
     *
     * @param hasAgency the has agency
     */
    public void setHasAgency(String hasAgency) {
        this.hasAgency = hasAgency;
    }


}
