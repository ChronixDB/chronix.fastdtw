/*
 * Arrays.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package de.qaware.chronix.distance;


public final class DistanceFunctionFactory {
    public static DistanceFunction EUCLIDEAN_DIST_FN = new EuclideanDistance();
    public static DistanceFunction MANHATTAN_DIST_FN = new ManhattanDistance();
    public static DistanceFunction BINARY_DIST_FN = new BinaryDistance();

    public static DistanceFunction getDistFnByName(String distFnName) {
        switch (distFnName) {
            case "EuclideanDistance":
                return EUCLIDEAN_DIST_FN;
            case "ManhattanDistance":
                return MANHATTAN_DIST_FN;
            case "BinaryDistance":
                return BINARY_DIST_FN;
            default:
                throw new IllegalArgumentException("There is no DistanceFunction for the name " + distFnName);
        }
    }
}