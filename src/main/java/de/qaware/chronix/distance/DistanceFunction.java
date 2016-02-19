/*
 * Arrays.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package de.qaware.chronix.distance;


public interface DistanceFunction {
    double calcDistance(double[] vector1, double[] vector2);
}