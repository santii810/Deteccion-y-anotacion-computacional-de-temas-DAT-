import parser.Parser
import reader.FileReader
import reader.FolderReader
import reader.Reader
import spock.lang.Specification
import writer.ParseWriter

class ReadAndParseManagerTest extends Specification {
    String RESOURCES_URL = "src/test/resources/"

    def "files parsed has saved"() {
        when:
        Reader reader = new FileReader(new File(RESOURCES_URL + filename));
        Parser parser = new Parser();
        ReadAndParseManager readAndParseManager = new ReadAndParseManager(reader, parser);
        readAndParseManager.parseToFile();
        String[] files = new File(ParseWriter.FOLDER).list();

        then:
        files.contains(filename.replace(".txt", ".json"));
        where:
        filename << ["ame06_a01.txt", "ame06_a02.txt", "ame06_a03.txt"]
    }

}
