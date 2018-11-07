package com.gerasimchuk.entities;


import javax.persistence.*;

/**
 * The type Manager.
 */
@Entity(name = "Managers")
@Table(name = "managers",schema = "mywwldatabase", catalog = "")
public class Manager {


    private int id;
    private User user;

    /**
     * Instantiates a new Manager.
     */
    public Manager() {
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
    @OneToOne(mappedBy = "manager")
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
