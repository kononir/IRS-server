package com.bsuir.inforetrsys.util;

import com.bsuir.inforetrsys.api.util.MetricCalculator;

public class MetricCalculatorImpl implements MetricCalculator {
    @Override
    public double calculateRecall(int a, int c) {
        return (double) a / ((double) a + (double) c);
    }

    @Override
    public double calculatePrecision(int a, int b) {
        return (double) a / ((double) a + (double) b);
    }

    @Override
    public double calculateAccuracy(int a, int b, int c, int d) {
        return ((double) a + (double) d) / ((double) a + (double) b + (double) c + (double) d);
    }

    @Override
    public double calculateError(int a, int b, int c, int d) {
        return ((double) b + (double) c) / ((double) a + (double) b + (double) c + (double) d);
    }

    @Override
    public double calculateFMeasure(int a, int b, int c) {
        return 2 / (1 / calculateRecall(a, c) + 1 / calculatePrecision(a, b));
    }
}
