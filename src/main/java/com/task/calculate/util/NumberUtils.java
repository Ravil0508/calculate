package com.task.calculate.util;

public class NumberUtils {

    private NumberUtils() {}

    public static Double roundDouble(Double num) {
        String formattedNum = String.format("%.2f", num);
        return Double.parseDouble(formattedNum.replace(",", "."));
    }
}
