/*
 * FastDTW.java   Jul 14, 2004
 *
 * Copyright (c) 2004 Stan Salvador
 * stansalvador@hotmail.com
 */

package de.qaware.chronix.dtw;


import de.qaware.chronix.timeseries.PAA;
import de.qaware.chronix.timeseries.TimeSeries;
import de.qaware.chronix.distance.DistanceFunction;

public final class FastDTW {

    final static int DEFAULT_SEARCH_RADIUS = 1;

    private FastDTW() {
        //avoid instances
    }

    public static double getWarpDistBetween(final TimeSeries tsI, final TimeSeries tsJ, final DistanceFunction distFn) {
        return fastDTW(tsI, tsJ, DEFAULT_SEARCH_RADIUS, distFn).getDistance();
    }


    public static double getWarpDistBetween(final TimeSeries tsI, final TimeSeries tsJ, int searchRadius, final DistanceFunction distFn) {
        return fastDTW(tsI, tsJ, searchRadius, distFn).getDistance();
    }


    public static WarpPath getWarpPathBetween(final TimeSeries tsI, final TimeSeries tsJ, final DistanceFunction distFn) {
        return fastDTW(tsI, tsJ, DEFAULT_SEARCH_RADIUS, distFn).getPath();
    }


    public static WarpPath getWarpPathBetween(final TimeSeries tsI, final TimeSeries tsJ, int searchRadius, final DistanceFunction distFn) {
        return fastDTW(tsI, tsJ, searchRadius, distFn).getPath();
    }


    public static TimeWarpInfo getWarpInfoBetween(final TimeSeries tsI, final TimeSeries tsJ, int searchRadius, final DistanceFunction distFn) {
        return fastDTW(tsI, tsJ, searchRadius, distFn);
    }


    private static TimeWarpInfo fastDTW(final TimeSeries tsI, final TimeSeries tsJ, int searchRadius, final DistanceFunction distFn) {
        if (searchRadius < 0)
            searchRadius = 0;

        final int minTSsize = searchRadius + 2;

        if ((tsI.size() <= minTSsize) || (tsJ.size() <= minTSsize)) {
            // Perform full Dynamic Time Warping.
            return DTW.getWarpInfoBetween(tsI, tsJ, distFn);
        } else {
            final PAA shrunkI = new PAA(tsI, tsI.size() / 2);
            final PAA shrunkJ = new PAA(tsJ, tsJ.size() / 2);

            // Determine the search window that constrains the area of the cost matrix that will be evaluated based on
            //    the warp path found at the previous resolution (smaller time series).
            final SearchWindow window = new ExpandedResWindow(tsI, tsJ, shrunkI, shrunkJ, FastDTW.getWarpPathBetween(shrunkI, shrunkJ, searchRadius, distFn), searchRadius);

            // Find the optimal warp path through this search window constraint.
            return DTW.getWarpInfoBetween(tsI, tsJ, window, distFn);
        }
    }
}
