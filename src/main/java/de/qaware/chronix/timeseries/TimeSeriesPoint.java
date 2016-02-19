/*
 * TimeSeriesPoint.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package de.qaware.chronix.timeseries;

public class TimeSeriesPoint {
    private double[] measurements;
    private int hashCode;


    public TimeSeriesPoint(double[] values) {
        hashCode = 0;
        measurements = new double[values.length];
        for (int x = 0; x < values.length; x++) {
            hashCode += new Double(values[x]).hashCode();
            measurements[x] = values[x];
        }
    }

    public double get(int dimension) {
        return measurements[dimension];
    }

    public void set(int dimension, double newValue) {
        hashCode -= new Double(measurements[dimension]).hashCode();
        measurements[dimension] = newValue;
        hashCode += new Double(newValue).hashCode();
    }


    public double[] toArray() {
        return measurements;
    }


    public int size() {
        return measurements.length;
    }


    public String toString() {
        String outStr = "(";
        for (int x = 0; x < measurements.length; x++) {
            outStr += measurements[x];
            if (x < measurements.length - 1)
                outStr += ",";
        }
        outStr += ")";

        return outStr;
    }


    public boolean equals(Object o) {
        if (this == o)
            return true;
        else if (o instanceof TimeSeriesPoint) {
            final double[] testValues = ((TimeSeriesPoint) o).toArray();
            if (testValues.length == measurements.length) {
                for (int x = 0; x < measurements.length; x++)
                    if (measurements[x] != testValues[x])
                        return false;

                return true;
            } else
                return false;
        } else
            return false;
    }


    public int hashCode() {
        return this.hashCode;
    }

}
