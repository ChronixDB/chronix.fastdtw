package de.qaware.chronix.distance

import spock.lang.Specification
import spock.lang.Unroll

/**
 * Unit test for the binary distance
 * @author f.lautenschlager
 */
class BinaryDistanceTest extends Specification {

    @Unroll
    def "test calc binary distance for values: #firstValues and #secondValues. Expecting result #expectedResult"() {
        when:
        def result = new BinaryDistance().calcDistance(firstValues, secondValues)

        then:
        result == expectedResult

        where:
        expectedResult << [1d, 0d]
        firstValues << [[2.0d, 4.5d] as double[], [1.0d, 2d] as double[]]
        secondValues << [[3.0d, 6.5d] as double[], [1.0d, 2d] as double[]]
    }
}
