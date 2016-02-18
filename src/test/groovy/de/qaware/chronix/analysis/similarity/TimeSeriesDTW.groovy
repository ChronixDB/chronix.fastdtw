package de.qaware.chronix.analysis.similarity

import de.qaware.chronix.dtw.FastDTW
import de.qaware.chronix.timeseries.TimeSeries
import de.qaware.chronix.timeseries.TimeSeriesPoint
import de.qaware.chronix.util.DistanceFunctionFactory
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by f.lautenschlager on 18.02.2016.
 */
class TimeSeriesDTW extends Specification {

    @Unroll
    def "test warp path of two time series: search radius: #searchRadius, faster than: #maxTime ms."() {
        given:
        println("Waiting for input.")
        //System.in.read()

        def distFn = DistanceFunctionFactory.getDistFnByName("EuclideanDistance")
        def tsI = fillTimeSeries("CPU-Load.csv", 1)
        def tsJ = fillTimeSeries("CPU-Load.csv", 2)


        when:
        def start = System.currentTimeMillis();
        def info = FastDTW.getWarpInfoBetween(tsI, tsJ, searchRadius, distFn)
        def end = System.currentTimeMillis();

        then:
        info.getDistance() == distance
        info.getPath().size()
        (end - start) < maxTime

        where:
        searchRadius << [1, 5, 10, 15, 20, 25, 30]
        //That are the result of the default implementation
        distance << [79106.02999997968d, 69642.54999996412d, 62786.65999995535d, 61584.96999994975d, 61644.99999994942d, 60684.239999951926d, 59660.479999950316d]
        maxTime << [1000, 1000, 1000, 2000, 2000, 2000, 2500]
    }


    static TimeSeries fillTimeSeries(String filePath, int column) {
        def timeSeries = new TimeSeries(1)
        def inputStream = TimeSeriesDTW.getResourceAsStream("/$filePath")
        def index = 0;
        def firstLine = true;
        inputStream.eachLine() {
            if (!firstLine) {
                def columns = it.split(",")
                if (columns.length == 2 && column == 1) {
                    def point = new TimeSeriesPoint([columns[column] as double])
                    timeSeries.addLast(index, point)
                    index++;
                } else if (columns.length == 3 && column == 2) {
                    def point = new TimeSeriesPoint([columns[column] as double])
                    timeSeries.addLast(index, point)
                    index++;
                }
            } else {
                firstLine = false
            }
        }
        return timeSeries;
    }

}
