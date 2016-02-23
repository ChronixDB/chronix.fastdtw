package de.qaware.chronix.dtw

import de.qaware.chronix.distance.DistanceFunctionEnum
import de.qaware.chronix.distance.DistanceFunctionFactory
import de.qaware.chronix.timeseries.MultivariateTimeSeries
import spock.lang.Specification

/**
 * Unit test for the fast dtw implementation
 * @author f.lautenschlager
 */
class FastDTWTest extends Specification {

    def "test getWarpInfoBetween"() {
        given:
        def dist = DistanceFunctionFactory.getDistanceFunction(DistanceFunctionEnum.EUCLIDEAN)

        when:
        def result = FastDTW.getWarpInfoBetween(ts1, ts2, 1, dist)
        then:
        result.distance == expected

        where:
        expected << [0, 0, 0, 9]
        ts1 << [tsWith(0), tsWith(1), tsWith(0), tsWith([1.0, 2.0, 3.0] as double[])]
        ts2 << [tsWith(0), tsWith(0), tsWith(1), tsWith([4.0, 5.0, 6.0] as double[])]
    }

    def MultivariateTimeSeries tsWith(int points) {
        def ts = new MultivariateTimeSeries(1)
        for (int j = 0; j < points; j++) {
            ts.add(j, j + points)
        }
        return ts
    }

    def MultivariateTimeSeries tsWith(double[] points) {
        def ts = new MultivariateTimeSeries(1)
        points.eachWithIndex { double entry, int i ->
            ts.add(i, entry)
        }

        return ts
    }
}
