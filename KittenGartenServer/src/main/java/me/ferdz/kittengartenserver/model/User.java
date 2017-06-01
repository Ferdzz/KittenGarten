package me.ferdz.kittengartenserver.model;

import java.util.StringTokenizer;
import java.util.UUID;

/**
 * Created by 1452284 on 2016-08-26.
 */
public class User extends MinUser implements IMinimalizable<MinUser> {
    private String password;

    public User(String username) {
        super(username);
    }

    public User(int id, String username) {
        super(id, username);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if((o instanceof User)) {
            User user = (User)o;
            if(user.getPassword().equals(this.getPassword()) && user.getUsername().equals(this.getUsername()))
                return true;
        }
        return false;
    }

    @Override
    public MinUser minimalize() {
        MinUser minUser = new MinUser(id, username);
        minUser.setEmail(email);
        return minUser;
    }
}
