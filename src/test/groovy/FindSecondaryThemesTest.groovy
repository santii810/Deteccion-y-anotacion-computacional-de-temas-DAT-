import groovy.util.slurpersupport.GPathResult
import groovy.util.slurpersupport.NodeChild
import lombok.extern.slf4j.Slf4j
import mapper.ObjectMapper
import parser.Parser
import reader.FileReader
import reader.Reader
import spock.lang.Specification

import java.util.stream.Collectors

@Slf4j
class FindSecondaryThemesTest extends Specification {
    static final String RESOURCES_URL = "src/test/resources/TextosAnalizados/"

    def "Check	example5"() {
        when:
        List<Integer> themeSizes = getListOfThemeSizes("ej5.txt")
        then:
        themeSizes.contains(13)
        themeSizes.contains(1)
    }


    def "Check	example6"() {
        when:
        List<Integer> themeSizes = getListOfThemeSizes("ej6.txt")
        then:
        themeSizes.contains(2)
        themeSizes.contains(1)
    }

    def "Check	example7"() {
        when:
        List<Integer> themeSizes = getListOfThemeSizes("ej7.txt")
        then:
        themeSizes.contains(2)
        themeSizes.contains(1)
    }

    def "Check	example8"() {
        when:
        List<Integer> themeSizes = getListOfThemeSizes("ej8.txt")
        then:
        themeSizes.contains(3)
        themeSizes.contains(7)
    }

    def "Check	example11"() {
        when:
        List<Integer> themeSizes = getListOfThemeSizes("ej11.txt")
        then:
        themeSizes.contains(2)
        themeSizes.contains(7)
    }


    def "Check	example13"() {
        when:
        List<Integer> themeSizes = getListOfThemeSizes("ej13.txt")
        then:
        themeSizes.contains(2)
        themeSizes.contains(2)
    }

    def "Check	example14"() {
        when:
        List<Integer> themeSizes = getListOfThemeSizes("ej14.txt")
        then:
        themeSizes.contains(3)
        themeSizes.contains(1)
        themeSizes.contains(2)
        themeSizes.contains(12)
    }

    def "Check	example15"() {
        when:
        List<Integer> themeSizes = getListOfThemeSizes("ej15.txt")
        then:
        themeSizes.contains(2)
        themeSizes.contains(1)
    }


    def "Check	example20"() {
        when:
        List<Integer> themeSizes = getListOfThemeSizes("ej20.txt")
        then:
        themeSizes.contains(1)
        themeSizes.contains(3)
    }


    def "Check	example31"() {
        when:
        List<Integer> themeSizes = getListOfThemeSizes("ej31.txt")
        then:
        themeSizes.contains(1)
        themeSizes.contains(5)
    }

    static List<Integer> getListOfThemeSizes(String filename) {
        Reader reader = new FileReader(new File(RESOURCES_URL + filename))
        MainHelper mainHelper = new MainHelper(reader, new Parser(), new ObjectMapper())
        mainHelper.process()

        String outputFile = filename.split("\\.")[0] + ".xml"
        GPathResult xml = new XmlSlurper().parse(new File('.\\out\\Parsedfiles\\' + outputFile))
        List<NodeChild> pivots = xml.sentences.sentence.depthFirst().findAll { it.name() == 'theme' }
        return pivots.stream().map({
            it -> it.children().size()
        }).collect(Collectors.toList())
    }

}
