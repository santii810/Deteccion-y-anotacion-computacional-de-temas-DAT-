import lombok.extern.slf4j.Slf4j;
import mapper.ObjectMapper;
import mapper.ObjectsMapped;
import model.xml.*;
import parser.Parser;
import parser.ParserResponse;
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
    static long startTime;
    static long newFileTime;


    public MainHelper(Reader reader, Parser parser, ObjectMapper objectMapper, Processor processor) {
        this.reader = reader;
        this.parser = parser;
        this.objectMapper = objectMapper;
        this.processor = processor;
        startTime = System.currentTimeMillis();
        newFileTime = System.currentTimeMillis();
    }

    public MainHelper(Reader reader) {
        this.reader = reader;
        this.parser = new Parser();
        this.objectMapper = new ObjectMapper();
        this.processor = new Processor();
        startTime = System.currentTimeMillis();
        newFileTime = System.currentTimeMillis();
    }


    public void process() throws IOException, JAXBException {
        ProcessedOutput output = new ProcessedOutput();
        while (reader.hasMoreContent()) {
            FileFragment fileFragment = reader.read();
            if (!fileFragment.isNullOrEmpty()) {
                ParserResponse parserResponse = parser.send(fileFragment.getText());
                if (parserResponse.getHttpStatus() == 200) {
                    ObjectsMapped objects = new ObjectsMapped(objectMapper.mapResponse(parserResponse));
                    processor.process(objects, output);
                    if (fileFragment.isEndOfFile()) {
                        output.setRef(Utils.extractRefFromFilename(fileFragment.getFilename()));
                        Marshaller.marshall(output, createOutputFilename(fileFragment.getFilename()));
                        output = new ProcessedOutput();
                        log.info("File + " + fileFragment.getFilename() + " time: " + Utils.getElapsedTime(this.newFileTime, System.currentTimeMillis()));
                        this.newFileTime = System.currentTimeMillis();
                    }
                } else {
                    SentenceXml sentenceXml = new SentenceXml();
                    sentenceXml.setText(fileFragment.getText());
                    sentenceXml.setState(State.KO);
                    sentenceXml.setError("Sentence too large");
                    log.error("Parser response status code : " + parserResponse.getHttpStatus());
                    log.error(parserResponse.getBody());
                }
            }
        }

        //
        log.info("\n\nTotal time: " + Utils.getElapsedTime(this.startTime, System.currentTimeMillis()));


    }

    private String createOutputFilename(String inputFilename) {
        String fileWithoutExtesion = inputFilename.split("\\.")[0];
        return fileWithoutExtesion + FILE_OUTPUT_EXT;
    }
}
