import parser.Parser
import processor.NoProcessor
import processor.Processor
import processor.SimpleProcessor
import reader.FileReader
import reader.Reader
import spock.lang.Specification
import writer.ParseWriter

class ProcessorTest extends Specification {
    String RESOURCES_URL = "src/test/resources/SimpliestTexts/"

    def "process a simpliest text"() {
        when:
        Reader reader = new FileReader(new File(RESOURCES_URL + filename));
        Parser parser = new Parser();
//        Processor processor = new NoProcessor();
        Processor processor = new SimpleProcessor();
        ReadAndParseManager readAndParseManager = new ReadAndParseManager(reader, parser, processor);
        readAndParseManager.process();
        String[] files = new File(ParseWriter.FOLDER).list();

        then:
        files.contains(filename.replace(".txt", ".xml"));
        where:
        filename << ["Text2.txt"]
    }
}
