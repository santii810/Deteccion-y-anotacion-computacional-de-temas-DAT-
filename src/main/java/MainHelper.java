import mapper.ObjectMapper;
import mapper.ObjectsMapped;
import parser.Parser;
import parser.ParserResponse;
import processor.ProcessedOutput;
import processor.Processor;
import reader.FileFragment;
import reader.Reader;
import writer.Marshaller;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class MainHelper {

    private static final String FILE_OUTPUT_EXT = ".xml";
    private Reader reader;
    private Parser parser;
    private ObjectMapper objectMapper;
    private Processor processor;


    public MainHelper(Reader reader, Parser parser, ObjectMapper objectMapper) {
        this.reader = reader;
        this.parser = parser;
        this.objectMapper = objectMapper;
        //TODO revisar :(
        this.processor = new Processor();
    }

    public void process() throws IOException, JAXBException {
        ProcessedOutput output = new ProcessedOutput();
        while (reader.hasMoreContent()) {
            FileFragment fileFragment = reader.read();
            if (!fileFragment.isNullOrEmpty()) {
                ParserResponse parserResponse = parser.send(fileFragment.getText());
                ObjectsMapped objects = new ObjectsMapped(objectMapper.mapResponse(parserResponse));
                output.getSentences().addAll(processor.process(objects));
                if (fileFragment.isEndOfFile()) {
                    Marshaller.marshall(output, createOutputFilename(fileFragment.getFilename()));
                    output = new ProcessedOutput();
                }
            }
        }
    }

    private String createOutputFilename(String inputFilename) {
        String fileWithoutExtesion = inputFilename.split("\\.")[0];
        return fileWithoutExtesion + FILE_OUTPUT_EXT;
    }
}
