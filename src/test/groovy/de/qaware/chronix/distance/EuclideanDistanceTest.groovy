package de.qaware.chronix.distance

import spock.lang.Specification

/**
 * Unit test for the euclidean distance function
 * @author f.lautenschlager
 */
class EuclideanDistanceTest extends Specification {
    def "test calcDistance"() {
        when:
        def result = new EuclideanDistance().calcDistance(firstValues, secondValues)

        then:
        result == expectedResult

        where:
        expectedResult << [2.23606797749979d, 0d]
        firstValues << [[2.0d, 4.5d] as double[], [1.0d, 2d] as double[]]
        secondValues << [[3.0d, 6.5d] as double[], [1.0d, 2d] as double[]]
    }
}
