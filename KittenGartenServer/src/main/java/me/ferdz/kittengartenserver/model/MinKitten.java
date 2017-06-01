package me.ferdz.kittengartenserver.model;

import com.google.gson.annotations.Expose;

import java.util.StringTokenizer;

/**
 * Created by 1452284 on 2016-09-22.
 */
public class MinKitten {
    protected int id;
    protected String name;
    protected byte[] encodedImage;
    protected int renterId;

   public MinKitten(String name) {
        this(-1, name);
    }

    public MinKitten(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getEncodedImage() {
        return encodedImage;
    }

    public void setEncodedImage(byte[] encodedImage) {
        this.encodedImage = encodedImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRenterId() {
        return renterId;
    }

    public void setRenterId(int renterId) {
        this.renterId = renterId;
    }
}
