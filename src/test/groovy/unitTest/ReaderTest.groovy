package unitTest

import reader.FileFragment
import reader.FileReader
import reader.FolderReader
import reader.Reader
import spock.lang.Specification
import utils.TestConstraints
import utils.TestUtils

class ReaderTest extends Specification {

    def "Files splitted has size under limit "() {
        when:
        FileFragment str = new FileReader(new File(TestConstraints.RESOURCES_URL + name)).read()
        then:
        str.text.length() < Reader.MAX_SIZE
        where:
        name << TestConstraints.FILE_LIST
    }

    def "Content of documents dont have the character '-'"() {
        when:
        String fileWithReader = ""
        FileReader reader = new FileReader(new File(TestConstraints.RESOURCES_URL + name))
        while (reader.hasMoreContent()) {
            fileWithReader += reader.read().getText()
        }

        then:
        !fileWithReader.contains('-')

        where:
        name << TestConstraints.FILE_LIST
    }


    def "Return error when file not exist"() {
        setup:
        String filename = "hola.txt"

        when:
        new FileReader(new File(TestConstraints.RESOURCES_URL + filename)).read()

        then:
        thrown FileNotFoundException
    }

    def "Reader not read same two times"() {
        setup:
        String filename = "ame06_a01.txt"
        FileReader reader = new FileReader(new File(TestConstraints.RESOURCES_URL + filename))

        when:
        String firstRead = reader.read()
        String secondRead = reader.read()

        then:
        firstRead != secondRead
    }

    def "Folder reader can read all files"() {
        when:
        FolderReader reader = new FolderReader(new File(TestConstraints.RESOURCES_URL + folder))
        List<String> filesnames = new File(TestConstraints.RESOURCES_URL + folder).list()
        def filesReaded = [].toSet()
        while (reader.hasMoreContent()) {
            FileFragment fragment = reader.read()
            filesReaded.add(fragment.filename)
        }
        then:
        filesnames.size() == filesReaded.size()

        where:
        folder << ["AmE06_files/AmE06_A"]
    }


    def "Folder reader real all files recursively"() {
        when:
        FolderReader reader = new FolderReader(new File(TestConstraints.RESOURCES_URL + folder))
        List<String> filenames = TestUtils.getAllFilenames(new File(TestConstraints.RESOURCES_URL + folder))

        def filesReaded = [].toSet()
        while (reader.hasMoreContent()) {
            FileFragment fragment = reader.read()
            filesReaded.add(fragment.filename)
        }
        then:
        filenames.size() == filesReaded.size()

        where:
        folder << ["AmE06_files"]
    }


    def "Return error when folder not exist"() {
        setup:
        String folder = "abdc"
        when:
        new FolderReader(new File(TestConstraints.RESOURCES_URL + folder)).read()
        then:
        thrown NullPointerException
    }

    def "Return error when folder is empty"() {
        setup:
        File folder = new File(TestConstraints.RESOURCES_URL + "temporalFolderToTesting")
        folder.mkdir()
        when:
        new FolderReader(folder).read()
        then:
        thrown FileNotFoundException
        cleanup:
        folder.delete()

    }

}