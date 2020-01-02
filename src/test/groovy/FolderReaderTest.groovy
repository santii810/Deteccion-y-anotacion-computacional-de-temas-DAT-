import reader.FileFragment
import reader.FolderReader
import spock.lang.Specification
import utils.ResourceUtils

class FolderReaderTest extends Specification {

    def "read all files"() {
        when:
        FolderReader reader = new FolderReader(new File(ResourceUtils.RESOURCES_URL + folder))
        List<String> filesnames = new File(ResourceUtils.RESOURCES_URL + folder).list()
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


    def "read recursive folder files"() {
        when:
        FolderReader reader = new FolderReader(new File(ResourceUtils.RESOURCES_URL + folder))
        List<String> filenames = getAllFilenames(new File(ResourceUtils.RESOURCES_URL + folder))

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

    def "folder not exists"() {
        setup:
        String folder = "abdc"
        when:
        new FolderReader(new File(ResourceUtils.RESOURCES_URL + folder)).read()
        then:
        thrown NullPointerException
    }

    def "folder is empty"() {
        setup:
        new File(ResourceUtils.RESOURCES_URL + "temporalFolderToTesting").mkdir()
        String folder = "temporalFolderToTesting"
        when:
        new FolderReader(new File(ResourceUtils.RESOURCES_URL + folder)).read()
        then:
        thrown FileNotFoundException
        cleanup:
        new File("temporalFolderToTesting").delete()

    }

    List<String> getAllFilenames(File folder) {
        List<String> toret = []
        folder.listFiles().each { file ->
            if (file.isDirectory()) {
                toret.addAll(getAllFilenames(file))
            } else {
                toret.add(file.getName())
            }
        }
        return toret
    }
}