/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 QAware GmbH
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package de.qaware.chronix.timeseries;


import de.qaware.chronix.dt.IntList;
import de.qaware.chronix.dt.LongList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stan Salvador (stansalvador@hotmail.com)
 * @author f.lautenschlager
 */
public class MultivariateTimeSeries {

    private final IntList labels;   // labels for each column
    private final LongList times;
    private final List<double[]> values;    // ArrayList of TSValues.. no time

    public MultivariateTimeSeries() {
        labels = new IntList();
        times = new LongList();
        values = new ArrayList<>();
    }

    public MultivariateTimeSeries(int numOfDimensions) {
        this();
        for (int x = 0; x <= numOfDimensions; x++)
            labels.add(x);
    }

    public int size() {
        return times.size();
    }

    public int numOfDimensions() {
        return labels.size() - 1;
    }

    public double getTimeAtNthPoint(int n) {
        return times.get(n);
    }

    public IntList getDimensions() {
        return labels;
    }

    public void setDimensions(IntList newLabels) {
        labels.clear();
        labels.addAll(newLabels);
    }

    public double getMeasurement(int pointIndex, int valueIndex) {
        return values.get(pointIndex)[valueIndex];
    }

    public double[] getMeasurementVector(int pointIndex) {
        return values.get(pointIndex);
    }

    public void setMeasurement(int pointIndex, int valueIndex, double newValue) {
        values.get(pointIndex)[valueIndex] = newValue;
    }

    public void add(long time, double[] values) {
        if (labels.size() != values.length + 1)  // labels include a label for time
            throw new InternalError("ERROR:  The TSValues: " + values + " contains the wrong number of values. " + "expected:  " + labels.size() + ", " + "found: " + values.length);

        if ((this.size() > 0) && (time <= times.get(times.size() - 1)))
            throw new InternalError("ERROR:  The point being inserted at the " + "end of the time series does not have " + "the correct time sequence. ");

        times.add(time);
        this.values.add(values);
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
        for (int r = 0; r < times.size(); r++) {
            // The rest of the value on the row.
            final double[] values = this.values.get(r);
            for (int c = 0; c < values.length; c++)
                outStr.append(values[c]);

            if (r < times.size() - 1)
                outStr.append("\n");
        }
        return outStr.toString();
    }
}
