package me.ferdz.kittengarten.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Date;
import java.util.UUID;

import me.ferdz.kittengarten.R;

/**
 *
 * :3
 *
 *
 * Created by 1452284 on 2016-08-25.
 */
public class Kitten extends MinKitten implements IMinimalizable<MinKitten> {

    private int weight;
    private Date birthDate;
    private User renter;

    public Kitten(String name) {
        this(-1, name);
    }

    public Kitten(int id, String name) {
        super(id, name);
        this.birthDate = new Date();
        this.renterId = -1;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public User getRenter() {
        return renter;
    }

    public void setRenter(User user) {
        this.renterId = user.getId();
        this.renter = user;
    }

    @Override
    public MinKitten minimalize() {
        MinKitten minKitten = new MinKitten(id, name);
        minKitten.setEncodedImage(encodedImage);
        minKitten.setRenterId(renterId);
        return minKitten;
    }
}
