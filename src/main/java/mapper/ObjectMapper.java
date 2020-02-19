package mapper;

import model.Sentence;
import model.Word;
import org.json.JSONObject;
import parser.ParserResponse;

import java.util.ArrayList;
import java.util.List;

public class ObjectMapper {

    public List<Sentence> mapResponse(ParserResponse response) {
        List<Sentence> sentences = new ArrayList<>();
        String result = (String) new JSONObject(response.getBody()).get("result");
        for (String sentenceStr : result.split("\\n\\n")) {
            sentences.add(processFragmentToSentence(sentenceStr));
        }
        return sentences;
    }

    private Sentence processFragmentToSentence(String sentenceStr) {
        Sentence sentence = new Sentence();
        for (String line : sentenceStr.split("\\n")) {
            if (line.startsWith("# text")) sentence.setText(line.substring(9));
            if (!line.startsWith("#")) {
                Word word = processToWords(line);
                sentence.getWords().put(word.getId(), word);
            }
        }
        return sentence;
    }

    private Word processToWords(String line) {
        Word word = new Word();
        String[] attibs = line.split("\t");
        word.setId(Integer.parseInt(attibs[0]));
        word.setForm(attibs[1]);
        word.setLemma(attibs[2]);
        word.setXPosTag(attibs[4]);
        word.setHead(Integer.parseInt(attibs[6]));
        word.setDepRel(attibs[7]);
        word.setDeps(attibs[8]);
        word.setMisc(attibs[9]);
        return word;
    }
}
