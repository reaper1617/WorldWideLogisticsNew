package com.gerasimchuk.dto;

/**
 * Id Data Transfer Object
 *
 * @author Reaper
 * @version 1.0
 */
public class IdDTO {
    private String id;

    /**
     * Instantiates a new Id dto.
     */
    public IdDTO() {
    }

    /**
     * Instantiates a new Id dto.
     *
     * @param id the id
     */
    public IdDTO(String id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IdDTO)) return false;

        IdDTO idDTO = (IdDTO) o;

        return id != null ? id.equals(idDTO.id) : idDTO.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
