package com.gerasimchuk.entities;


import javax.persistence.*;

/**
 * The type Admin.
 */
@Entity(name = "Admins")
@Table(name = "admins", schema = "mywwldatabase", catalog = "")
public class Admin {

    private int id;
    private User user;

    /**
     * Instantiates a new Admin.
     */
    public Admin() {
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
     * Gets user.
     *
     * @return the user
     */
    @OneToOne(mappedBy = "admin")
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }


}
