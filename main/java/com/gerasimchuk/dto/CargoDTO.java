package com.gerasimchuk.dto;

import com.gerasimchuk.entities.Cargo;

/**
 * Cargo Data Transfer Object
 *
 * @author Reaper
 * @version 1.0
 */
public class CargoDTO  {
    private String id;
    private String personalNumber;
    private String name;
    private String weight;
    private String cityFrom;
    private String cityTo;
    private String status;

    /**
     * Instantiates a new Cargo dto.
     */
    public CargoDTO() {
    }

    /**
     * Instantiates a new Cargo dto.
     *
     * @param id             the id
     * @param personalNumber the personal number
     * @param name           the name
     * @param weight         the weight
     * @param cityFrom       the city from
     * @param cityTo         the city to
     * @param status         the status
     */
    public CargoDTO(String id, String personalNumber, String name, String weight, String cityFrom, String cityTo, String status) {
        this.id = id;
        this.personalNumber = personalNumber;
        this.name = name;
        this.weight = weight;
        this.cityFrom = cityFrom;
        this.cityTo = cityTo;
        this.status = status;
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
     * Gets personal number.
     *
     * @return the personal number
     */
    public String getPersonalNumber() {
        return personalNumber;
    }

    /**
     * Sets personal number.
     *
     * @param personalNumber the personal number
     */
    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
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
     * Gets weight.
     *
     * @return the weight
     */
    public String getWeight() {
        return weight;
    }

    /**
     * Sets weight.
     *
     * @param weight the weight
     */
    public void setWeight(String weight) {
        this.weight = weight;
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
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CargoDTO)) return false;

        CargoDTO cargoDTO = (CargoDTO) o;

        if (personalNumber != null ? !personalNumber.equals(cargoDTO.personalNumber) : cargoDTO.personalNumber != null)
            return false;
        if (!name.equals(cargoDTO.name)) return false;
        if (!weight.equals(cargoDTO.weight)) return false;
        if (!cityFrom.equals(cargoDTO.cityFrom)) return false;
        if (!cityTo.equals(cargoDTO.cityTo)) return false;
        return status != null ? status.equals(cargoDTO.status) : cargoDTO.status == null;
    }

    @Override
    public int hashCode() {
        int result = personalNumber != null ? personalNumber.hashCode() : 0;
        result = 31 * result + name.hashCode();
        result = 31 * result + weight.hashCode();
        result = 31 * result + cityFrom.hashCode();
        result = 31 * result + cityTo.hashCode();
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }


}
