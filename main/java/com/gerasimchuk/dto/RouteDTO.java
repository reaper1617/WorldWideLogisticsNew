package com.gerasimchuk.dto;


/**
 * Route Data Transfer Object
 *
 * @author Reaper
 * @version 1.0
 */
public class RouteDTO {
    private String id;
    private String cityFrom;
    private String cityTo;
    private String distance;

    /**
     * Instantiates a new Route dto.
     */
    public RouteDTO() {
    }

    /**
     * Instantiates a new Route dto.
     *
     * @param id       the id
     * @param cityFrom the city from
     * @param cityTo   the city to
     * @param distance the distance
     */
    public RouteDTO(String id, String cityFrom, String cityTo, String distance) {
        this.id = id;
        this.cityFrom = cityFrom;
        this.cityTo = cityTo;
        this.distance = distance;
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
     * Gets city from.
     *
     * @return the city from
     */
    public String getCityFrom() {
        return cityFrom;
    }

    /**
     * Sets city from.
     *
     * @param cityFrom the city from
     */
    public void setCityFrom(String cityFrom) {
        this.cityFrom = cityFrom;
    }

    /**
     * Gets city to.
     *
     * @return the city to
     */
    public String getCityTo() {
        return cityTo;
    }

    /**
     * Sets city to.
     *
     * @param cityTo the city to
     */
    public void setCityTo(String cityTo) {
        this.cityTo = cityTo;
    }

    /**
     * Gets distance.
     *
     * @return the distance
     */
    public String getDistance() {
        return distance;
    }

    /**
     * Sets distance.
     *
     * @param distance the distance
     */
    public void setDistance(String distance) {
        this.distance = distance;
    }
}
