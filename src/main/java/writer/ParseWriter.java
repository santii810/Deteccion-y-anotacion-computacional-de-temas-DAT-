package writer;

import parser.ParserResponse;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ParseWriter {
    public static final String FOLDER = "./out/parsedFiles/";

    public static void save(List<ParserResponse> responseList, String filename) throws IOException {
        createStructure(filename);
        FileWriter fileWriter = new FileWriter(FOLDER + filename);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for (ParserResponse response : responseList) {
            printWriter.println(response.getBody());
        }
        printWriter.close();
    }

    private static void createStructure(String filename) {
        File file = new File(FOLDER);
        if (!file.exists()) file.mkdirs();

        String directoryName = filename.replace(new File(filename).getName(), "");
        File directory = new File(FOLDER + "/" + directoryName);
        if(!directory.exists()) directory.mkdirs();
    }
}
