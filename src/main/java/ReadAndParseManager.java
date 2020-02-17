import model.xml.ProcesedOutput;
import parser.Parser;
import parser.ParserResponse;
import processor.ObjectMapper;
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
    private ObjectMapper objectMapper;
    private Processor processor;


    public ReadAndParseManager(Reader reader, Parser parser, ObjectMapper objectMapper) {
        this.reader = reader;
        this.parser = parser;
        this.objectMapper = objectMapper;
        //TODO revisar :(
        this.processor = new Processor();
    }

    public ProcesedOutput process() throws IOException, JAXBException {
        List<ParserResponse> responseList = new ArrayList<>();
        String lastFilename = null;
        ProcesedOutput out = new ProcesedOutput();
        while (reader.hasMoreContent()) {
            FileFragment fileFragment = reader.read();
            // Gaurdamos lo último si cambia el nombre de fichero
            if (lastFilename != fileFragment.getFilename() && lastFilename != null) {
                //TODO arreglar esto, no se va a guardar bien cuando los ficheros sean grandes
                objectMapper.process(responseList, out);
                responseList = new ArrayList<>();
            }
            lastFilename = fileFragment.getFilename();
            responseList.add(parser.send(fileFragment.getText()));
        }
        objectMapper.process(responseList, out);
        processor.process(out);


        Marshaller.marshall(out, createOutputFilename(lastFilename));
        return out;
    }


    private String createOutputFilename(String inputFilename) {
        String fileWithoutExtesion = inputFilename.split("\\.")[0];
        return fileWithoutExtesion + FILE_OUTPUT_EXT;
    }


}
