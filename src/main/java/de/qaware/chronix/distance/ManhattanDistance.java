/*
 * Arrays.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package de.qaware.chronix.distance;


public final class ManhattanDistance implements DistanceFunction {
    public double calcDistance(double[] vector1, double[] vector2) {
        double diffSum = 0.0;
        for (int x = 0; x < vector1.length; x++)
            diffSum += Math.abs(vector1[x] - vector2[x]);

        return diffSum;
    }

}