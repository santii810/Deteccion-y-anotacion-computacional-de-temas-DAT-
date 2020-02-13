package processor;

import model.xml.*;
import org.json.JSONObject;
import parser.ParserResponse;

import java.util.ArrayList;
import java.util.List;

public class ObjectMapper {

    public void process(List<ParserResponse> parserResponses, ProcesedOutput out) {
        out.getSentences().addAll(processToObjects(parserResponses));
    }


    private List<Sentence> processToObjects(List<ParserResponse> parserResponses) {
        List<Sentence> sentences = new ArrayList<>();
        for (ParserResponse response : parserResponses) {
            String result = (String) new JSONObject(response.getBody()).get("result");
            for (String sentenceStr : result.split("\\n\\n")) {
                processToSentence(sentences, sentenceStr);
            }
        }
        return sentences;
    }

    private void processToSentence(List<Sentence> sentences, String sentenceStr) {
        Sentence sentence = new Sentence();
        sentences.add(sentence);
        for (String line : sentenceStr.split("\\n")) {
            if (line.startsWith("# text")) sentence.setText(line.substring(9));
            if (!line.startsWith("#")) {
                processToWords(sentence.getWords(), line);
            }
        }
    }

    private void processToWords(List<Word> words, String line) {
        Word word = new Word();
        words.add(word);
        String[] attibs = line.split("\t");
        word.setId(Integer.parseInt(attibs[0]));
        word.setForm(attibs[1]);
        word.setLemma(attibs[2]);
        word.setXPosTag(attibs[4]);
        word.setHead(Integer.parseInt(attibs[6]));
        word.setDepRel(attibs[7]);
        word.setDeps(attibs[8]);
        word.setMisc(attibs[9]);
    }
}
