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
package de.qaware.chronix.dtw;


import de.qaware.chronix.dt.IntList;
import de.qaware.chronix.matrix.ColMajorCell;

import java.util.NoSuchElementException;

/**
 * @author Stan Salvador (stansalvador@hotmail.com)
 * @author f.lautenschlager
 */
public final class WarpPath {
    private final IntList tsIindexes;   // ArrayList of Integer
    private final IntList tsJindexes;   // ArrayList of Integer

    public WarpPath() {
        tsIindexes = new IntList();
        tsJindexes = new IntList();
    }


    public WarpPath(int initialCapacity) {
        tsIindexes = new IntList(initialCapacity);
        tsJindexes = new IntList(initialCapacity);
    }


    public int size() {
        return tsIindexes.size();
    }

    public int minI() {
        return tsIindexes.get(tsIindexes.size() - 1);
    }

    public int minJ() {
        return tsJindexes.get(tsJindexes.size() - 1);
    }

    public int maxI() {
        return tsIindexes.get(0);
    }

    public int maxJ() {
        return tsJindexes.get(0);
    }

    public void addFirst(int i, int j) {
        tsIindexes.add(i);
        tsJindexes.add(j);
    }

    public void addLast(int i, int j) {
        tsIindexes.add(0, i);
        tsJindexes.add(0, j);
    }


    public IntList getMatchingIndexesForI(int i) {
        int index = tsIindexes.indexOf(i);
        if (index < 0)
            throw new InternalError("ERROR:  index '" + i + " is not in the " + "warp path.");
        final IntList matchingJs = new IntList(tsIindexes.size());
        while (index < tsIindexes.size() && tsIindexes.get(index) == i)
            matchingJs.add(tsJindexes.get(index++));

        return matchingJs;
    }


    public IntList getMatchingIndexesForJ(int j) {
        int index = tsJindexes.indexOf(j);
        if (index < 0)
            throw new InternalError("ERROR:  index '" + j + " is not in the " + "warp path.");
        final IntList matchingIs = new IntList(tsJindexes.size());
        while (index < tsJindexes.size() && tsJindexes.get(index) == j)
            matchingIs.add(tsIindexes.get(index++));

        return matchingIs;
    }


    // Create a new WarpPath that is the same as THIS WarpPath, but J is the reference template, rather than I.
    public WarpPath invertedCopy() {
        final WarpPath newWarpPath = new WarpPath();
        for (int x = 0; x < tsIindexes.size(); x++)
            newWarpPath.addLast(tsJindexes.get(x), tsIindexes.get(x));

        return newWarpPath;
    }


    // Swap I and J so that the warp path now indicates that J is the template rather than I.
    public void invert() {
        for (int x = 0; x < tsIindexes.size(); x++) {
            final int temp = tsIindexes.get(x);
            tsIindexes.set(x, tsJindexes.get(x));
            tsJindexes.set(x, temp);
        }
    }


    public ColMajorCell get(int index) {
        if ((index > this.size()) || (index < 0))
            throw new NoSuchElementException();
        else
            return new ColMajorCell(tsIindexes.get(tsIindexes.size() - index - 1), tsJindexes.get(tsJindexes.size() - index - 1));
    }


    public String toString() {
        StringBuilder outStr = new StringBuilder("[");
        for (int x = 0; x < tsIindexes.size(); x++) {
            outStr.append("(").append(tsIindexes.get(x)).append(",").append(tsJindexes.get(x)).append(")");
            if (x < tsIindexes.size() - 1)
                outStr.append(",");
        }
        outStr.append("]");

        return outStr.toString();
    }


    public boolean equals(Object obj) {
        if ((obj instanceof WarpPath))  // trivial false test
        {
            final WarpPath p = (WarpPath) obj;
            if ((p.size() == this.size()) && (p.maxI() == this.maxI()) && (p.maxJ() == this.maxJ())) // less trivial reject
            {
                // Compare each value in the warp path for equality
                for (int x = 0; x < this.size(); x++)
                    if (!(this.get(x).equals(p.get(x))))
                        return false;

                return true;
            } else
                return false;
        } else
            return false;
    }


    public int hashCode() {
        return tsIindexes.hashCode() * tsJindexes.hashCode();
    }

}
