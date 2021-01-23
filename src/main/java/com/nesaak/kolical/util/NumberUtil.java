package com.nesaak.kolical.util;

public class NumberUtil {

    // Hard coded to round to hundreds place
    public static double round(double value) {
        return (double) Math.round(value * 100) / 100;
    }

}
