package com.gerasimchuk.dto;

/**
 * Admin Data Transfer Object
 *
 * @author Reaper
 * @version 1.0
 */
public class AdminDTO {
    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String password;
    private String personalNumber;

    /**
     * Instantiates a new Admin dto.
     */
    public AdminDTO() {
    }

    /**
     * Instantiates a new Admin dto.
     *
     * @param id             the id
     * @param firstName      the first name
     * @param middleName     the middle name
     * @param lastName       the last name
     * @param password       the password
     * @param personalNumber the personal number
     */
    public AdminDTO(String id, String firstName, String middleName, String lastName, String password, String personalNumber) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.password = password;
        this.personalNumber = personalNumber;
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
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets middle name.
     *
     * @return the middle name
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Sets middle name.
     *
     * @param middleName the middle name
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdminDTO)) return false;

        AdminDTO adminDTO = (AdminDTO) o;

        if (!firstName.equals(adminDTO.firstName)) return false;
        if (!middleName.equals(adminDTO.middleName)) return false;
        return lastName.equals(adminDTO.lastName);
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + middleName.hashCode();
        result = 31 * result + lastName.hashCode();
        return result;
    }
}
