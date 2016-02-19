/*
 * WindowMatrix.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package de.qaware.chronix.dtw;


class WindowMatrix implements CostMatrix {
    private CostMatrix windowCells;

    public WindowMatrix(SearchWindow searchWindow) {
        windowCells = new MemoryResidentMatrix(searchWindow);

    }

    public void put(int col, int row, double value) {
        windowCells.put(col, row, value);
    }


    public double get(int col, int row) {
        return windowCells.get(col, row);
    }


    public int size() {
        return windowCells.size();
    }
}
