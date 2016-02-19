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
package de.qaware.chronix.distance;

/**
 * @author Stan Salvador (stansalvador@hotmail.com)
 * @author f.lautenschlager
 */
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