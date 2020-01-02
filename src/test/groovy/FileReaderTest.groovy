import reader.FileFragment
import reader.FileReader
import reader.Reader
import spock.lang.Specification
import utils.ResourceUtils

class FileReaderTest extends Specification {

    def "test size limit "() {
        when:
        FileFragment str = new FileReader(new File(ResourceUtils.RESOURCES_URL + name)).read()

        then:
        str.text.length() < Reader.MAX_SIZE

        where:
        name << ResourceUtils.FILE_LIST
    }


    def "full file reading test"() {
        when:
        String fullFile = ""
        Scanner scanner = new Scanner(new File(ResourceUtils.RESOURCES_URL + name))
        while (scanner.hasNext()) fullFile += scanner.nextLine()

        String fileWithReader = ""
        FileReader reader = new FileReader(new File(ResourceUtils.RESOURCES_URL + name))
        while (reader.hasMoreContent()) {
            fileWithReader += reader.read().getText()
        }

        then:
        fileWithReader == fullFile

        where:
        name << ResourceUtils.FILE_LIST
    }

    def "first characters "() {
        when:
        FileFragment str = new FileReader(new File(ResourceUtils.RESOURCES_URL + name)).read()

        then:
        str.text.startsWith(startText)

        where:
        name << ResourceUtils.FILE_LIST
        startText << ResourceUtils.FIRST_CHARACTERS_OF_FILES
    }

    def "file not exists"() {
        setup:
        String filename = "hola.txt"

        when:
        new FileReader(new File(ResourceUtils.RESOURCES_URL + filename)).read();

        then:
        thrown FileNotFoundException
    }

    def "not read same two times"() {
        setup:
        String filename = "ame06_a01.txt";
        FileReader reader = new FileReader(new File(ResourceUtils.RESOURCES_URL + filename))

        when:
        String firstRead = reader.read();
        String secondRead = reader.read();

        then:
        firstRead != secondRead
    }


}