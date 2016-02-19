/*
 * TimeSeries.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package de.qaware.chronix.timeseries;


import java.util.ArrayList;
import java.util.List;


public class TimeSeries {

    private final List<String> labels;   // labels for each column
    private final List<Double> timeReadings;        // ArrayList of Double
    private final List<TimeSeriesPoint> tsArray;    // ArrayList of TimeSeriesPoint.. no time

    public TimeSeries() {
        labels = new ArrayList<>();
        timeReadings = new ArrayList<>();
        tsArray = new ArrayList<>();
    }

    public TimeSeries(int numOfDimensions) {
        this();
        labels.add("Time");
        for (int x = 0; x < numOfDimensions; x++)
            labels.add("" + x);
    }

    public int size() {
        return timeReadings.size();
    }

    public int numOfDimensions() {
        return labels.size() - 1;
    }

    public double getTimeAtNthPoint(int n) {
        return timeReadings.get(n);
    }

    public List<String> getLabels() {
        return labels;
    }


    public void setLabels(List<String> newLabels) {
        labels.clear();
        for (int x = 0; x < newLabels.size(); x++)
            labels.add(newLabels.get(x));
    }

    public double getMeasurement(int pointIndex, int valueIndex) {
        return tsArray.get(pointIndex).get(valueIndex);
    }

    public double[] getMeasurementVector(int pointIndex) {
        return tsArray.get(pointIndex).toArray();
    }

    public void setMeasurement(int pointIndex, int valueIndex, double newValue) {
        tsArray.get(pointIndex).set(valueIndex, newValue);
    }

    public void add(double time, TimeSeriesPoint values) {
        if (labels.size() != values.size() + 1)  // labels include a label for time
            throw new InternalError("ERROR:  The TimeSeriesPoint: " + values +
                    " contains the wrong number of values. " +
                    "expected:  " + labels.size() + ", " +
                    "found: " + values.size());

        if ((this.size() > 0) && (time <= timeReadings.get(timeReadings.size() - 1)))
            throw new InternalError("ERROR:  The point being inserted at the " +
                    "end of the time series does not have " +
                    "the correct time sequence. ");

        timeReadings.add(time);
        tsArray.add(values);
    }


    public void normalize() {
        // Calculate the mean of each FD.
        final double[] mean = new double[this.numOfDimensions()];
        for (int col = 0; col < numOfDimensions(); col++) {
            double currentSum = 0.0;
            for (int row = 0; row < this.size(); row++)
                currentSum += this.getMeasurement(row, col);

            mean[col] = currentSum / this.size();
        }  // end for loop

        // Calculate the standard deviation of each FD.
        final double[] stdDev = new double[numOfDimensions()];
        for (int col = 0; col < numOfDimensions(); col++) {
            double variance = 0.0;
            for (int row = 0; row < this.size(); row++)
                variance += Math.abs(getMeasurement(row, col) - mean[col]);

            stdDev[col] = variance / this.size();
        }  // end for loop


        // Normalize the values in the data using the mean and standard deviation
        //    for each FD.  =>  Xrc = (Xrc-Mc)/SDc
        for (int row = 0; row < this.size(); row++) {
            for (int col = 0; col < numOfDimensions(); col++) {
                // Normalize data point.
                if (stdDev[col] == 0.0)   // prevent divide by zero errors
                    setMeasurement(row, col, 0.0);  // stdDev is zero means all pts identical
                else   // typical case
                    setMeasurement(row, col, (getMeasurement(row, col) - mean[col]) / stdDev[col]);
            }  // end for loop
        }  // end for loop
    }  // end normalize();


    public String toString() {
        final StringBuilder outStr = new StringBuilder();

        // Write the data for each row.
        for (int r = 0; r < timeReadings.size(); r++) {
            // The rest of the value on the row.
            final TimeSeriesPoint values = tsArray.get(r);
            for (int c = 0; c < values.size(); c++)
                outStr.append(values.get(c));

            if (r < timeReadings.size() - 1)
                outStr.append("\n");
        }
        return outStr.toString();
    }
}
