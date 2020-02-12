package processor;

import model.Sentence;
import model.xml.ProcesedOutput;
import model.xml.SentenceXml;
import org.json.JSONObject;
import parser.ParserResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleProcessor implements Processor {
    @Override
    public ProcesedOutput process(List<ParserResponse> parserResponses) {
        List<Sentence> sentences = convertToSentences(parserResponses);


        ProcesedOutput po = new ProcesedOutput();
        po.setSentenceXmls(sentences.stream().map(SentenceXml::new).collect(Collectors.toList()));
        return po;
    }


    private List<Sentence> convertToSentences(List<ParserResponse> parserResponses) {
        List<Sentence> sentences = new ArrayList<>();

        for (ParserResponse response : parserResponses) {
            String result = (String) new JSONObject(response.getBody()).get("result");
            for (String sentenceStr : result.split("\\n\\n")) {
                Sentence sentence = new Sentence();
                sentences.add(sentence);
                List<String> words = new ArrayList<>();
                for (String line : sentenceStr.split("\\n")) {
                    System.out.println(line);
                    if (line.startsWith("# text")) sentence.setText(line.substring(9));
                    if (!line.startsWith("#")) {
                        words.add(line);
                    }
                }
                sentence.setWords(words);
            }
        }
        return sentences;
    }
}
