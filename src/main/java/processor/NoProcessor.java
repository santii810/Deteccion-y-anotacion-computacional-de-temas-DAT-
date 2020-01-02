package processor;

import model.ProcesedOutput;
import parser.ParserResponse;

import java.util.List;

public class NoProcessor implements Processor {

    @Override
    public ProcesedOutput process(List<ParserResponse> parserResponse) {
        ProcesedOutput procesedOutput = new ProcesedOutput();
        procesedOutput.setParserResponses(parserResponse);
        return procesedOutput;
    }
}
