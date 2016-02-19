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

/**
 * @author Stan Salvador (stansalvador@hotmail.com)
 * @author f.lautenschlager
 */
class PartialWindowMatrix implements CostMatrix {
    private static final double OUT_OF_WINDOW_VALUE = Double.POSITIVE_INFINITY;
    private double[] lastCol;
    private double[] currCol;
    private int currColIndex;
    private int minLastRow;
    private int minCurrRow;
    private final SearchWindow window;


    public PartialWindowMatrix(SearchWindow searchWindow) {
        window = searchWindow;

        if (window.maxI() > 0) {
            currCol = new double[window.maxJforI(1) - window.minJforI(1) + 1];
            currColIndex = 1;
            minLastRow = window.minJforI(currColIndex - 1);
        } else
            currColIndex = 0;

        minCurrRow = window.minJforI(currColIndex);
        lastCol = new double[window.maxJforI(0) - window.minJforI(0) + 1];
    }


    public void put(int col, int row, double value) {
        if (col == currColIndex)
            currCol[row - minCurrRow] = value;
        else if (col == currColIndex - 1)
            lastCol[row - minLastRow] = value;
        else if (col == currColIndex + 1) {
            lastCol = currCol;
            minLastRow = minCurrRow;
            currColIndex++;
            currCol = new double[window.maxJforI(col) - window.minJforI(col) + 1];
            minCurrRow = window.minJforI(col);

            currCol[row - minCurrRow] = value;
        } else
            throw new InternalError("A PartialWindowMatrix can only fill in 2 adjacentcolumns at a time");
    }


    public double get(int col, int row) {
        if ((row < window.minJforI(col)) || (row > window.maxJforI(col)))
            return OUT_OF_WINDOW_VALUE;
        else {
            if (col == currColIndex)
                return currCol[row - minCurrRow];
            else if (col == currColIndex - 1)
                return lastCol[row - minLastRow];
            else
                return OUT_OF_WINDOW_VALUE;
        }
    }


    public int size() {
        return lastCol.length + currCol.length;
    }


    public int windowSize() {
        return window.size();
    }

}
