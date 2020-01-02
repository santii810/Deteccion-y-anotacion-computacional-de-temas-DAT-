import parser.Parser;
import parser.ParserResponse;
import reader.FileFragment;
import reader.Reader;
import writer.ParseWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadAndParseManager {

    private Reader reader;
    private Parser parser;

    public ReadAndParseManager(Reader reader, Parser parser) {
        this.reader = reader;
        this.parser = parser;
    }

    public void parseToFile() throws IOException {
        List<ParserResponse> responseList = new ArrayList<>();
        String lastFilename = null;
        while (reader.hasMoreContent()) {
            FileFragment fileFragment = reader.read();
            // Gaurdamos lo último si cambia el nombre de fichero
            if (lastFilename != fileFragment.getFilename() && lastFilename != null) {
                ParseWriter.save(responseList, createOutputFilename(lastFilename));
                responseList = new ArrayList<>();
            }
            lastFilename = fileFragment.getFilename();
            responseList.add(parser.send(fileFragment.getText()));
        }
        ParseWriter.save(responseList, createOutputFilename(lastFilename));
    }

    private String createOutputFilename(String inputFilename) {
        String fileWithoutExtesion = inputFilename.split("\\.")[0];
        return fileWithoutExtesion + ".json";
    }


}
