import parser.Parser
import processor.NoProcessor
import processor.Processor
import reader.FileReader
import reader.Reader
import spock.lang.Specification
import writer.ParseWriter

class ReadAndParseManagerTest extends Specification {
    String RESOURCES_URL = "src/test/resources/"

    def "files parsed has saved"() {
        when:
        Reader reader = new FileReader(new File(RESOURCES_URL + filename))
        Parser parser = new Parser()
        Processor processor = new NoProcessor()
        ReadAndParseManager readAndParseManager = new ReadAndParseManager(reader, parser, processor)
        readAndParseManager.process()
        String[] files = new File(ParseWriter.FOLDER).list()

        then:
        files.contains(filename.replace(".txt", ".xml"))
        where:
        filename << ["ame06_a01.txt", "ame06_a02.txt", "ame06_a03.txt"]
    }

}
