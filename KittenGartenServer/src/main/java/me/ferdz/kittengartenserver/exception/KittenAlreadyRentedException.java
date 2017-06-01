package me.ferdz.kittengartenserver.exception;

/**
 * Created by 1452284 on 2016-10-20.
 */
public class KittenAlreadyRentedException extends RuntimeException {
    public KittenAlreadyRentedException() {
        super("This kitten is already rented!");
    }
}
