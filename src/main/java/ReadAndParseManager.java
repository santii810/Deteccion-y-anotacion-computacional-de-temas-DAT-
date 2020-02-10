import model.xml.ProcesedOutput;
import parser.Parser;
import parser.ParserResponse;
import processor.Processor;
import reader.FileFragment;
import reader.Reader;
import writer.Marshaller;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadAndParseManager {

    private static final String FILE_OUTPUT_EXT = ".xml";
    private Reader reader;
    private Parser parser;
    private Processor processor;


    public ReadAndParseManager(Reader reader, Parser parser, Processor processor) {
        this.reader = reader;
        this.parser = parser;
        this.processor = processor;
    }

    public void process() throws IOException, JAXBException {
        List<ParserResponse> responseList = new ArrayList<>();
        String lastFilename = null;
        while (reader.hasMoreContent()) {
            FileFragment fileFragment = reader.read();
            // Gaurdamos lo último si cambia el nombre de fichero
            if (lastFilename != fileFragment.getFilename() && lastFilename != null) {
                processAndSave(responseList, lastFilename);
                responseList = new ArrayList<>();
            }
            lastFilename = fileFragment.getFilename();
            responseList.add(parser.send(fileFragment.getText()));
        }
        processAndSave(responseList, lastFilename);
    }

    private void processAndSave(List<ParserResponse> responseList, String lastFilename) throws JAXBException {
        ProcesedOutput processedOutput = processor.process(responseList);
        Marshaller.marshall(processedOutput, createOutputFilename(lastFilename));
    }


    private String createOutputFilename(String inputFilename) {
        String fileWithoutExtesion = inputFilename.split("\\.")[0];
        return fileWithoutExtesion + FILE_OUTPUT_EXT;
    }


}
