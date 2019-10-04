package com.bsuir.inforetrsys.entity.table;

public class MetricsData {
    private double recall;
    private double precision;
    private double accuracy;
    private double error;
    private double fMeasure;

    public MetricsData(double recall, double precision, double accuracy, double error, double fMeasure) {
        this.recall = recall;
        this.precision = precision;
        this.accuracy = accuracy;
        this.error = error;
        this.fMeasure = fMeasure;
    }

    public double getRecall() {
        return recall;
    }

    public double getPrecision() {
        return precision;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public double getError() {
        return error;
    }

    public double getFMeasure() {
        return fMeasure;
    }
}
