package processor;

import model.xml.ProcesedOutput;
import parser.ParserResponse;

import java.util.List;

public interface Processor {

    ProcesedOutput process(List<ParserResponse> parserResponse);
}
