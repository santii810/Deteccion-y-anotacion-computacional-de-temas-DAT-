import groovy.json.JsonSlurper
import mapper.ObjectMapper
import parser.Parser
import reader.FileReader
import reader.Reader
import spock.lang.Shared
import spock.lang.Specification

class ExecutionTest extends Specification {
    String RESOURCES_URL = "src/test/resources/TextosAnalizados/"
    @Shared
    def solutions = [:]

    def setupSpec() {
        def jsonSlurper = new JsonSlurper()
        File fl = new File("src/test/resources/TextosAnalizados/Solutions.json")
        this.solutions = jsonSlurper.parse(fl)
    }

    def "Text extracted is ok"() {

        when:
        HashMap solution = solutions.get(files)[0]
        Reader reader = new FileReader(new File(RESOURCES_URL + solution.file))
        MainHelper mainHelper = new MainHelper(reader, new Parser(), new ObjectMapper())
        mainHelper.process()

        String outputFile = solution.file.split("\\.")[0] + ".xml"
        def xml = new XmlSlurper().parseText(new File("out/parsedFiles" + outputFile).text)
        println(xml)

        then:
//        xml.sentences.text == solutions.get(sol).text[0]
        true

        where:
        files << ["example31", "example1"]
    }


}
