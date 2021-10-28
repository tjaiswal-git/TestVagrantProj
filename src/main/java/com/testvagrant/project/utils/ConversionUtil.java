package com.testvagrant.project.utils;

public class ConversionUtil {

    public static String degreeToCelsius(String degreeTemp)
    {
        double tempinCelsius = (Double.parseDouble(degreeTemp) -32) * 5 / 9;
        return String.valueOf(Math.round(tempinCelsius));
    }
}
