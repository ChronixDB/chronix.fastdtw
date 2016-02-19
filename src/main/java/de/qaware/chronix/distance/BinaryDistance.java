/*
 * Arrays.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package de.qaware.chronix.distance;


import java.util.Arrays;

public final class BinaryDistance implements DistanceFunction {
    public double calcDistance(double[] vector1, double[] vector2) {
        if (Arrays.equals(vector1, vector2)) {
            return 0.0;
        } else {
            return 1.0;
        }
    }

}