import groovy.json.JsonSlurper
import groovy.util.slurpersupport.GPathResult
import groovy.util.slurpersupport.NodeChild
import lombok.extern.slf4j.Slf4j
import mapper.ObjectMapper
import parser.Parser
import processor.Processor
import reader.FileReader
import reader.Reader
import spock.lang.Specification

import java.util.stream.Collectors


@Slf4j
class FindSecondaryPivotsTest extends Specification {
    static final String RESOURCES_URL = "src/test/resources/TextosAnalizados/"

    def "Check	example5"() {
        when:
        List<String> pivots = checkProcess("ej5.txt")
        then:
        pivots.size() == 2
        pivots.contains("contains")

    }


    def "Check	example6"() {
        when:
        List<String> pivots = checkProcess("ej6.txt")
        then:
        pivots.size() == 2
        pivots.contains("is")
    }

    def "Check	example7"() {
        when:
        List<String> pivots = checkProcess("ej7.txt")
        then:
        pivots.size() == 2
        pivots.contains("has")
    }

    def "Check	example8"() {
        when:
        List<String> pivots = checkProcess("ej8.txt")
        then:
        pivots.size() == 2
        pivots.contains("gave")
    }

    def "Check	example11"() {
        when:
        List<String> pivots = checkProcess("ej11.txt")
        then:
        pivots.size() == 2
        pivots.contains("beat")
    }



    def "Check	example13"() {
        when:
        List<String> pivots = checkProcess("ej13.txt")
        then:
        pivots.size() == 2
        pivots.contains("playing")
    }

    def "Check	example14"() {
        when:
        List<String> pivots = checkProcess("ej14.txt")
        then:
        pivots.size() == 4
        pivots.contains("is")
        pivots.contains("deters")
        pivots.contains("caught")
    }

    def "Check	example15"() {
        when:
        List<String> pivots = checkProcess("ej15.txt")
        then:
        pivots.size() == 2
        pivots.contains("is")
    }


    def "Check	example20"() {
        when:
        List<String> pivots = checkProcess("ej20.txt")
        then:
        pivots.size() == 3
        pivots.contains("guess")
        pivots.contains("go")
        pivots.contains("put")
    }


    def "Check	example31"() {
        when:
        List<String> pivots = checkProcess("ej31.txt")
        then:
        pivots.size() == 2
        pivots.contains("'re")
        pivots.contains("know")
    }

    static List<String> checkProcess(String filename) {
        Reader reader = new FileReader(new File(RESOURCES_URL + filename))
        MainHelper mainHelper = new MainHelper(reader, new Parser(), new ObjectMapper(), new Processor())
        mainHelper.process()

        String outputFile = filename.split("\\.")[0] + ".xml"
        GPathResult xml = new XmlSlurper().parse(new File('.\\out\\Parsedfiles\\' + outputFile))
        List<NodeChild> pivots = xml.sentences.sentence.depthFirst().findAll { it.name() == 'pivot' }
        return pivots.stream().map({ it -> it.text() }).collect(Collectors.toList())
    }

}
