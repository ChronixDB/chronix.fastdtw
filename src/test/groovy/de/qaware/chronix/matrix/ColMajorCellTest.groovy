package de.qaware.chronix.matrix

import spock.lang.Specification

/**
 * Unit test for the ColMajorCell
 * @author f.lautenschlager
 */
class ColMajorCellTest extends Specification {

    def "test getCol and getRow"() {
        given:
        def colMajorCell = new ColMajorCell(0, 0)

        when:
        def col = colMajorCell.getCol()
        def row = colMajorCell.getRow()

        then:
        col == 0
        row == 0
    }

    def "test toString"() {
        given:
        def colMajorCell = new ColMajorCell(0, 0)

        when:
        def string = colMajorCell.toString()
        then:
        string.contains("col")
        string.contains("row")

    }

    def "test equals"() {
        given:
        def colMajorCell = new ColMajorCell(0, 0)

        when:
        def equals = colMajorCell.equals(others)
        then:
        equals == result
        colMajorCell.equals(colMajorCell)//always true

        where:
        others << [new Object(), null, new ColMajorCell(0, 0)]
        result << [false, false, true]
    }

    def "test hashCode"() {
        given:
        def colMajorCell = new ColMajorCell(0, 0)

        when:
        def hash = colMajorCell.hashCode()
        then:
        hash == new ColMajorCell(0, 0).hashCode()
        hash != new ColMajorCell(0, 1).hashCode()
    }
}
