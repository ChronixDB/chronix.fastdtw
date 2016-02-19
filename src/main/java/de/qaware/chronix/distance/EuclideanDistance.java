/*
 * Arrays.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package de.qaware.chronix.distance;


public final class EuclideanDistance implements DistanceFunction {
    public double calcDistance(double[] vector1, double[] vector2) {
        double sqSum = 0.0;
        for (int x = 0; x < vector1.length; x++)
            sqSum += Math.pow(vector1[x] - vector2[x], 2.0);

        return Math.sqrt(sqSum);
    }

}