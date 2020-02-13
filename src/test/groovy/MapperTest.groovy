import groovy.json.JsonSlurper
import model.xml.ProcesedOutput
import parser.Parser

import processor.ObjectMapper
import reader.FileReader
import reader.Reader
import spock.lang.Shared
import spock.lang.Specification

class MapperTest extends Specification {
    String RESOURCES_URL = "src/test/resources/TextosAnalizados/"
    @Shared
    def solutions = [:]

    def setupSpec() {
        def jsonSlurper = new JsonSlurper()
        File fl = new File("src/test/resources/TextosAnalizados/Solutions.json")
        HashMap obj = jsonSlurper.parse(fl)
        this.solutions = obj
    }

    def "Text extracted is ok"() {
        when:
        Reader reader = new FileReader(new File(RESOURCES_URL + solutions.get(sol).file[0]));
        Parser parser = new Parser();
        ObjectMapper mapper = new ObjectMapper();
        ReadAndParseManager readAndParseManager = new ReadAndParseManager(reader, parser, mapper);
        ProcesedOutput po = readAndParseManager.process();

        then:
        po.getSentences().get(0).getText() == solutions.get(sol).text[0]

        where:
        sol << ["example31", "example1"]
    }

    def "Pivot is correctly identified"() {
        when:
        Reader reader = new FileReader(new File(RESOURCES_URL + solutions.get(sol).file[0]));
        Parser parser = new Parser();
        ObjectMapper mapper = new ObjectMapper();
        ReadAndParseManager readAndParseManager = new ReadAndParseManager(reader, parser, mapper);
        ProcesedOutput po = readAndParseManager.process();

        then:
        po.getSentences().get(0).getPivot() == solutions.get(sol).pivot[0]

        where:
        sol << ["example31", "example1"]
    }

    def "Theme is correctly extracted"() {
        when:
        Reader reader = new FileReader(new File(RESOURCES_URL + solutions.get(sol).file[0]));
        Parser parser = new Parser();
        ObjectMapper mapper = new ObjectMapper();
        ReadAndParseManager readAndParseManager = new ReadAndParseManager(reader, parser, mapper);
        ProcesedOutput po = readAndParseManager.process();

        then:
        po.getSentences().get(0).getTheme() == solutions.get(sol).theme[0]

        where:
        sol << ["example31", "example1"]
    }

}
