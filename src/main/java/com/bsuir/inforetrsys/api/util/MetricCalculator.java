package com.bsuir.inforetrsys.api.util;

public interface MetricCalculator {
    double calculateRecall(int a, int c);

    double calculatePrecision(int a, int b);

    double calculateAccuracy(int a, int b, int c, int d);

    double calculateError(int a, int b, int c, int d);

    double calculateFMeasure(int a, int b, int c);
}
