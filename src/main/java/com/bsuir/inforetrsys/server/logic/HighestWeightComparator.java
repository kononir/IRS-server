package com.bsuir.inforetrsys.server.logic;

import java.util.Comparator;

public class HighestWeightComparator implements Comparator<Double> {
    @Override
    public int compare(Double o1, Double o2) {
        double subtraction = o1 - o2;
        int result;
        if (subtraction < 0) {
            result = -1;
        } else if (subtraction > 0) {
            result = 1;
        } else {
            result = 0;
        }
        return result;
    }
}
