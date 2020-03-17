import groovy.json.JsonSlurper
import groovy.util.slurpersupport.GPathResult
import lombok.extern.slf4j.Slf4j
import mapper.ObjectMapper
import parser.Parser
import reader.FileReader
import reader.Reader
import spock.lang.Specification


@Slf4j
class FindMainPivotsTest extends Specification {
    static final String RESOURCES_URL = "src/test/resources/TextosAnalizados/"
    static def jsonSolutions = [:]

    def setupSpec() {
        def jsonSlurper = new JsonSlurper()
        File fl = new File("src/test/resources/TextosAnalizados/Solutions.json")
        this.jsonSolutions = jsonSlurper.parse(fl)
    }

    def "Check	example1"() {
        expect:
        checkProcess("example1")
    }

    def "Check	example2"() {
        expect:
        checkProcess("example2")
    }

    def "Check	example3"() {
        expect:
        checkProcess("example3")
    }

    def "Check	example4"() {
        expect:
        checkProcess("example4")
    }

    def "Check	example5"() {
        expect:
        checkProcess("example5")
    }

    def "Check	example6"() {
        expect:
        checkProcess("example6")
    }

    def "Check	example7"() {
        expect:
        checkProcess("example7")
    }

    def "Check	example8"() {
        expect:
        checkProcess("example8")
    }

    def "Check	example9"() {
        expect:
        checkProcess("example9")
    }

    def "Check	example10"() {
        expect:
        checkProcess("example10")
    }

    def "Check	example11"() {
        expect:
        checkProcess("example11")
    }

    def "Check	example12"() {
        expect:
        checkProcess("example12")
    }

    def "Check	example13"() {
        expect:
        checkProcess("example13")
    }

    def "Check	example14"() {
        expect:
        checkProcess("example14")
    }

    def "Check	example15"() {
        expect:
        checkProcess("example15")
    }

    def "Check	example16"() {
        expect:
        checkProcess("example16")
    }

    def "Check	example17"() {
        expect:
        checkProcess("example17")
    }

    def "Check	example18"() {
        expect:
        checkProcess("example18")
    }

    def "Check	example19"() {
        expect:
        checkProcess("example19")
    }

    def "Check	example20"() {
        expect:
        checkProcess("example20")
    }

    def "Check	example21"() {
        expect:
        checkProcess("example21")
    }

    def "Check	example22"() {
        expect:
        checkProcess("example22")
    }

    def "Check	example23"() {
        expect:
        checkProcess("example23")
    }

    def "Check	example24"() {
        expect:
        checkProcess("example24")
    }

    def "Check	example25"() {
        expect:
        checkProcess("example25")
    }

    def "Check	example26"() {
        expect:
        checkProcess("example26")
    }

    def "Check	example27"() {
        expect:
        checkProcess("example27")
    }

    def "Check	example28"() {
        expect:
        checkProcess("example28")
    }

    def "Check	example29"() {
        expect:
        checkProcess("example29")
    }

    def "Check	example31"() {
        expect:
        checkProcess("example31")
    }

    def "Check	example33"() {
        expect:
        checkProcess("example33")
    }

    def "Check	example34"() {
        expect:
        checkProcess("example34")
    }

    def "Check	example35"() {
        expect:
        checkProcess("example35")
    }
    def "Check	example36"() {
        expect:
        checkProcess("example36")
    }


    static def checkProcess(String example) {
        HashMap solution = jsonSolutions.get(example)
        Reader reader = new FileReader(new File(RESOURCES_URL + solution.file))
        MainHelper mainHelper = new MainHelper(reader, new Parser(), new ObjectMapper())
        mainHelper.process()

        String outputFile = solution.file.split("\\.")[0] + ".xml"
        GPathResult xml = new XmlSlurper().parse(new File('.\\out\\Parsedfiles\\' + outputFile))

        if ((xml.sentences.sentence.@state as String) == "OK") {
            assert xml.sentences.sentence.@text == solution.text
            assert xml.sentences.sentence.result[0].pivot == solution.pivot
        } else {
            assert (xml.sentences.sentence.@state as String) == "KO"
        }

        true
    }

}
