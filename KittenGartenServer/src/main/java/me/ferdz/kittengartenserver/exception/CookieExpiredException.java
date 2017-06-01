package me.ferdz.kittengartenserver.exception;

/**
 * Created by 1452284 on 2016-11-24.
 */
public class CookieExpiredException extends RuntimeException {
    public CookieExpiredException(String s) {
        super(s);
    }
}
