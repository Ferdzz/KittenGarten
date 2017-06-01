package me.ferdz.kittengartenserver.model;

/**
 * Created by 1452284 on 2016-09-22.
 */
public class MinUser {
    protected int id;
    protected String username;
    protected String email;

    public MinUser(String username) {
        this.username = username;
    }

    public MinUser(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
