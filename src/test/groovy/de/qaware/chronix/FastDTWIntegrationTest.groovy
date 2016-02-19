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
package de.qaware.chronix

import de.qaware.chronix.distance.DistanceFunctionEnum
import de.qaware.chronix.distance.DistanceFunctionFactory
import de.qaware.chronix.dtw.FastDTW
import de.qaware.chronix.timeseries.MultivariateTimeSeries
import spock.lang.Specification
import spock.lang.Unroll
/**
 * Unit test to the FastDTW implementation
 * @author f.lautenschlager
 */
class FastDTWIntegrationTest extends Specification {

    @Unroll
    def "test warp path of two time series: search radius: #searchRadius, faster than: #maxTime ms."() {
        given:
        def distFn = DistanceFunctionFactory.getDistanceFunction(DistanceFunctionEnum.EUCLIDEAN)
        def tsI = fillTimeSeries("CPU-Load.csv", 1)
        def tsJ = fillTimeSeries("CPU-Load.csv", 2)

        when:
        def start = System.currentTimeMillis();
        def info = FastDTW.getWarpInfoBetween(tsI, tsJ, searchRadius, distFn)
        def end = System.currentTimeMillis();

        then:
        info.getDistance() == distance
        info.getPath().size()
        //(end - start) < maxTime

        println "FastDTW for search radius: $searchRadius took: ${end - start}"

        where:
        searchRadius << [1, 5, 10, 15, 20, 25, 30]
        //That are the result of the default implementation
        distance << [79106.02999997968d, 69642.54999996412d, 62786.65999995535d, 61584.96999994975d, 61644.99999994942d, 60684.239999951926d, 59660.479999950316d]
        maxTime << [2000, 2000, 2000, 3000, 3000, 3000, 4000]
    }


    static MultivariateTimeSeries fillTimeSeries(String filePath, int column) {
        def timeSeries = new MultivariateTimeSeries(1)
        def inputStream = FastDTWIntegrationTest.getResourceAsStream("/$filePath")
        def index = 0;
        def firstLine = true;
        inputStream.eachLine() {
            if (!firstLine) {
                def columns = it.split(",")
                if (columns.length == 2 && column == 1) {
                    timeSeries.add(index, [columns[column] as double] as double[])
                    index++;
                } else if (columns.length == 3 && column == 2) {
                    timeSeries.add(index, [columns[column] as double] as double[])
                    index++;
                }
            } else {
                firstLine = false
            }
        }
        return timeSeries
    }

}
