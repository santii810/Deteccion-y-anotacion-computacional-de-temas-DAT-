package processor;

import model.ProcesedOutput;
import parser.ParserResponse;

import java.util.List;

public interface Processor {

    ProcesedOutput process(List<ParserResponse> parserResponse);
}
