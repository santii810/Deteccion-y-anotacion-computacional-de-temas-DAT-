import lombok.extern.slf4j.Slf4j;
import mapper.ObjectMapper;
import mapper.ObjectsMapped;
import parser.Parser;
import parser.ParserResponse;
import processor.ProcessedOutput;
import processor.Processor;
import reader.FileFragment;
import reader.Reader;
import utils.Utils;
import writer.Marshaller;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Slf4j
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
                if (parserResponse.getHttpStatus() == 200) {
                    ObjectsMapped objects = new ObjectsMapped(objectMapper.mapResponse(parserResponse));
                    output.getSentences().addAll(processor.process(objects));
                    if (fileFragment.isEndOfFile()) {
                        output.setRef(Utils.extractRefFromFilename(fileFragment.getFilename()));
                        Marshaller.marshall(output, createOutputFilename(fileFragment.getFilename()));
                        output = new ProcessedOutput();
                        log.info("File + " + fileFragment.getFilename() + " time: " + Utils.getElapsedTime(Main.newFileTime, System.currentTimeMillis()));
                        Main.newFileTime = System.currentTimeMillis();
                    }
                } else {
                    log.error("Parser response status code : " + parserResponse.getHttpStatus());
                    log.error(parserResponse.getBody());
                }
            }
        }
    }

    private String createOutputFilename(String inputFilename) {
        String fileWithoutExtesion = inputFilename.split("\\.")[0];
        return fileWithoutExtesion + FILE_OUTPUT_EXT;
    }
}
