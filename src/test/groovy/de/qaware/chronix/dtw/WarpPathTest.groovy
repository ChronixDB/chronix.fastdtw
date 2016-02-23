package de.qaware.chronix.dtw

import spock.lang.Specification

/**
 * Unit test for the warp path
 * @author f.lautenschlager
 */
class WarpPathTest extends Specification {
    def "test size"() {
        given:
        def warpPath = new WarpPath(10)

        when:
        warpPath.add(1, 1)
        warpPath.add(2, 4)
        warpPath.add(3, 8)

        def element = warpPath.get(1)

        then:
        warpPath.maxI() == 1
        warpPath.maxJ() == 1
        warpPath.minI() == 3
        warpPath.minJ() == 8
        warpPath.size() == 3

        element.col == 2
        element.row == 4
    }


    def "test toString"() {
        given:
        def warpPath = new WarpPath(10)
        when:
        def string = warpPath.toString()
        then:
        !string.isEmpty()
    }

    def "test equals"() {
        given:
        def warpPath = new WarpPath(10)

        when:
        def equals = warpPath.equals(others)
        then:
        equals == result
        warpPath.equals(warpPath)//always true

        where:
        others << [new Object(), null, new WarpPath(10)]
        result << [false, false, true]
    }

    def "test hashCode"() {
        given:
        def colMajorCell = new WarpPath(10)
        colMajorCell.add(1, 1)

        when:
        def hash = colMajorCell.hashCode()
        then:
        def other = new WarpPath(10)
        other.add(1, 1)

        hash == other.hashCode()
        hash != new WarpPath(10).hashCode()
    }
}
