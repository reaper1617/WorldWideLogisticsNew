package com.gerasimchuk.entities;

import com.gerasimchuk.utils.JSONconvertable;

import javax.persistence.*;

/**
 * The type User.
 */
@Entity(name = "Users")
@Table(name = "users", schema = "mywwldatabase", catalog = "")
public class User implements JSONconvertable {
    private int id;
    private String name;
    private String middleName;
    private String lastName;
    private String personalNumber;
    private String password;
    private Driver driver;
    private Manager manager;
    private Admin admin;

    /**
     * Instantiates a new User.
     */
    public User() {
    }

    /**
     * Instantiates a new User.
     *
     * @param name           the name
     * @param middleName     the middle name
     * @param lastName       the last name
     * @param personalNumber the personal number
     * @param password       the password
     * @param driver         the driver
     * @param manager        the manager
     * @param admin          the admin
     */
    public User(String name,
                String middleName,
                String lastName,
                String personalNumber,
                String password,
                Driver driver,
                Manager manager,
                Admin admin) {
        this.name = name;
        this.middleName = middleName;
        this.lastName = lastName;
        this.personalNumber = personalNumber;
        this.password = password;
        this.driver = driver;
        this.manager = manager;
        this.admin = admin;
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
    @Column(name = "name", nullable = false)
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
     * Gets middle name.
     *
     * @return the middle name
     */
    @Column(name = "middlename", nullable = false)
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
    @Column(name = "lastname", nullable = false)
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

    /**
     * Gets personal number.
     *
     * @return the personal number
     */
    @Column(name = "personal_number", nullable = false, unique = true)
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
     * Gets password.
     *
     * @return the password
     */
    @Column(name = "password", nullable = false)
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
     * Gets driver.
     *
     * @return the driver
     */
    @OneToOne
    @JoinColumn(name = "driver_id")
    public Driver getDriver() {
        return driver;
    }

    /**
     * Sets driver.
     *
     * @param driver the driver
     */
    public void setDriver(Driver driver) {
        this.driver = driver;
    }


    /**
     * Gets manager.
     *
     * @return the manager
     */
    @OneToOne
    @JoinColumn(name = "manager_id")
    public Manager getManager() {
        return manager;
    }

    /**
     * Sets manager.
     *
     * @param manager the manager
     */
    public void setManager(Manager manager) {
        this.manager = manager;
    }


    /**
     * Gets admin.
     *
     * @return the admin
     */
    @OneToOne
    @JoinColumn(name = "admin_id")
    public Admin getAdmin() {
        return admin;
    }

    /**
     * Sets admin.
     *
     * @param admin the admin
     */
    public void setAdmin(Admin admin) {
        this.admin = admin;
    }


    @Override
    public String convertToJSONString() {
        StringBuilder result = new StringBuilder("{");
        result.append("\"id\":\"").append(id).append("\",");
        result.append("\"name\":\"").append(name).append("\",");
        result.append("\"middleName\":\"").append(middleName).append("\",");
        result.append("\"lastName\":\"").append(lastName).append("\",");
        result.append("\"personalNumber\":\"").append(personalNumber).append("\"}");
        return result.toString();
    }
}
