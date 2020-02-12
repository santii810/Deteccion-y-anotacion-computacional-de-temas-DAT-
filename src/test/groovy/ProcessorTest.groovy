import groovy.json.JsonSlurper
import model.xml.ProcesedOutput
import parser.Parser
import processor.Processor
import processor.SimpleProcessor
import reader.FileReader
import reader.Reader
import spock.lang.Shared
import spock.lang.Specification

class ProcessorTest extends Specification {
    String RESOURCES_URL = "src/test/resources/TextosAnalizados/"
    @Shared
    def solutions = [:]

    def setup() {
        def jsonSlurper = new JsonSlurper()
        File fl = new File("src/test/resources/TextosAnalizados/Solutions.json")
        HashMap obj = jsonSlurper.parse(fl)
        this.solutions = obj
    }

    def "Text extracted is ok"() {
        when:
        Reader reader = new FileReader(new File(RESOURCES_URL + examples));
        Parser parser = new Parser();
        Processor processor = new SimpleProcessor();
        ReadAndParseManager readAndParseManager = new ReadAndParseManager(reader, parser, processor);
        ProcesedOutput po = readAndParseManager.process();

        then:
        po.getSentenceXmls().get(0).getText() == examples.text

        where:
        examples << [solutions.get("example31")]
    }

    def "Pivot is correctly identified"() {
        when:
        Reader reader = new FileReader(new File(RESOURCES_URL + file));
        Parser parser = new Parser();
        Processor processor = new SimpleProcessor();
        ReadAndParseManager readAndParseManager = new ReadAndParseManager(reader, parser, processor);
        ProcesedOutput po = readAndParseManager.process();

        then:
        po.getSentenceXmls().get(0).getPivot() == pivot

        where:
        file      | pivot
        "Ej1.txt" | "scored"
        "Ej2.txt" | "is"
    }

    def "Theme is correctly extracted"() {
        when:
        Reader reader = new FileReader(new File(RESOURCES_URL + file));
        Parser parser = new Parser();
        Processor processor = new SimpleProcessor();
        ReadAndParseManager readAndParseManager = new ReadAndParseManager(reader, parser, processor);
        ProcesedOutput po = readAndParseManager.process();

        then:
        po.getSentenceXmls().get(0).getTheme() == theme

        where:
        file      | theme
        "Ej1.txt" | "City counciliors"
        "Ej2.txt" | "It"
    }

}
