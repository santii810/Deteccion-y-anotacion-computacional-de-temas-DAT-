package unitTest

class TestUtils {

   static  List<String> getAllFilenames(File folder) {
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
