import spock.lang.Specification


class ParserTest extends Specification {

    def "It creates a parser from a string and parses big-endian bytes"() {
        given:
        def parser = new Parser('thingy:2b')
        when:
        def result = parser.parse([0x72, 0x73] as List<Byte>)
        then:
        result == [
                thingy: 0x7372,
        ]
    }
    def "It creates a parser from a string and parses little-endian bytes"() {
        given:
        def parser = new Parser('thingy:2l')
        when:
        def result = parser.parse([0x72, 0x73] as List<Byte>)
        then:
        result == [
                thingy: 0x7273,
        ]
    }

    def "It creates a parser from a string and parses two little-endian shorts"() {
        given:
        def parser = new Parser('thingy:2l,thing2:2b')
        when:
        def result = parser.parse([0x72, 0x73, 0x74, 0x75] as List<Byte>)
        then:
        result == [
                thingy: 0x7273,
                thing2: 0x7574
        ]
    }

    def "It copes with an omitted type specifier"() {
        given:
        def parser = new Parser('thingy:2,thing2:2b')
        when:
        def result = parser.parse([0x72, 0x73, 0x74, 0x75] as List<Byte>)
        then:
        result == [
                thingy: 0x7273,
                thing2: 0x7574
        ]
    }
    def "It copes with an underscore in the name"() {
        given:
        def parser = new Parser('thing_1:2,thing_2:2b')
        when:
        def result = parser.parse([0x72, 0x73, 0x74, 0x75] as List<Byte>)
        then:
        result == [
                thing_1: 0x7273,
                thing_2: 0x7574
        ]
    }

    def "It creates a parser from a string and parses a byte buffer little-endian shorts"() {
        given:
        def parser = new Parser('thingy:2l,thing2:12a')
        when:
        def result = parser.parse([0x72, 0x73, 0x74, 0x75, 0x76, 0x77, 0x74, 0x75, 0x76, 0x77,0x74, 0x75, 0x76, 0x77] as List<Byte>)
        then:
        result == [
                thingy: 0x7273,
                thing2: [0x74, 0x75, 0x76, 0x77, 0x74, 0x75, 0x76, 0x77,0x74, 0x75, 0x76, 0x77]
        ]
    }


}