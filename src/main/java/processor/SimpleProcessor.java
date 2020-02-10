package processor;

import model.xml.Sentence;
import model.xml.ProcesedOutput;
import org.json.JSONObject;
import parser.ParserResponse;

import java.util.ArrayList;
import java.util.List;

public class SimpleProcessor implements Processor {
    @Override
    public ProcesedOutput process(List<ParserResponse> parserResponses) {
        List<Sentence> sentences = convertToSentences(parserResponses);
        ProcesedOutput po = new ProcesedOutput();
        po.setSentences(sentences);
        return po;
    }


    private List<Sentence> convertToSentences(List<ParserResponse> parserResponses) {
        List<Sentence> sentences = new ArrayList<>();

        for (ParserResponse response : parserResponses) {
            String result = (String) new JSONObject(response.getBody()).get("result");
            for (String sentenceStr : result.split("\\n\\n")) {
                Sentence sentence = new Sentence();
                sentences.add(sentence);
                for (String line : sentenceStr.split("\\n")) {
                    if (line.startsWith("# text")) sentence.setText(line.substring(9));
                    if (!line.startsWith("#")) {

                    }
                }


            }
        }
        return sentences;
    }
}
