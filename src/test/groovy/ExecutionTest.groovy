import groovy.json.JsonSlurper
import groovy.util.slurpersupport.GPathResult
import mapper.ObjectMapper
import parser.Parser
import reader.FileReader
import reader.Reader
import spock.lang.Shared
import spock.lang.Specification

class ExecutionTest extends Specification {
    String RESOURCES_URL = "src/test/resources/TextosAnalizados/"
    static def jsonSolutions = [:]
    static def filesToTest = ["example31", "example1", "example4", "example5"/*, "example6", "example7"*/]

    def setupSpec() {
        def jsonSlurper = new JsonSlurper()
        File fl = new File("src/test/resources/TextosAnalizados/Solutions.json")
        this.jsonSolutions = jsonSlurper.parse(fl)
    }

    def "Text extracted is ok"() {
        when:
        HashMap solution = jsonSolutions.get(files)[0]
        Reader reader = new FileReader(new File(RESOURCES_URL + solution.file))
        MainHelper mainHelper = new MainHelper(reader, new Parser(), new ObjectMapper())
        mainHelper.process()

        String outputFile = solution.file.split("\\.")[0] + ".xml"
        GPathResult xml = new XmlSlurper().parse(new File('.\\out\\Parsedfiles\\' + outputFile))
        then:
        xml.sentences.sentence.@text == solution.text

        where:
        files << filesToTest
    }
    def "Pivot extracted is ok"() {
        when:
        HashMap solution = jsonSolutions.get(files)[0]
        Reader reader = new FileReader(new File(RESOURCES_URL + solution.file))
        MainHelper mainHelper = new MainHelper(reader, new Parser(), new ObjectMapper())
        mainHelper.process()

        String outputFile = solution.file.split("\\.")[0] + ".xml"
        GPathResult xml = new XmlSlurper().parse(new File('.\\out\\Parsedfiles\\' + outputFile))
        then:
        xml.sentences.sentence.pivot.@form == solution.pivot

        where:
        files << filesToTest
    }


}
