package de.qaware.chronix.distance

import spock.lang.Specification

/**
 * Unit test for the manhattan distance
 * @author f.lautenschlager
 */
class ManhattanDistanceTest extends Specification {

    def "test calcDistance"() {
        when:
        def result = new ManhattanDistance().calcDistance(firstValues, secondValues)

        then:
        result == expectedResult

        where:
        expectedResult << [3.0d, 0d]
        firstValues << [[2.0d, 4.5d] as double[], [1.0d, 2d] as double[]]
        secondValues << [[3.0d, 6.5d] as double[], [1.0d, 2d] as double[]]
    }
}
