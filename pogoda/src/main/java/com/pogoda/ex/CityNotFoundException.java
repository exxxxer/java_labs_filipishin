package com.pogoda.ex;

public class CityNotFoundException extends Exception {
    public CityNotFoundException(String msg) {
        super(msg);
    }
}
