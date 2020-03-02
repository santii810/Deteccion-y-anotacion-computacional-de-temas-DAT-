package processor;

import mapper.ObjectsMapped;
import model.Sentence;
import model.Word;
import model.xml.SentenceXml;
import model.xml.State;

import java.util.*;
import java.util.stream.Collectors;

public class Processor {


    public List<SentenceXml> process(ObjectsMapped out) {
        List<SentenceXml> toret = new ArrayList<>();
        for (Sentence sentence : out.getSentences()) {
            String text = sentence.getText().replace("\"", "");
            //FIXME esta comprobación no debería estár en un punto tan avanzado del análisis
            if (!text.isEmpty()) {
                SentenceXml xml = new SentenceXml();
                toret.add(xml);
                xml.setText(text);
                int pivotId = findMainPivot(sentence);
                if (pivotId == -1) {
                    xml.setState(State.KO);
                } else {
                    xml.setState(State.OK);
                    xml.setPivot(sentence.getWords().get(pivotId));
                    if (pivotId == 1) xml.getTheme().add(xml.getPivot());
                    xml.setTheme(sentence.getWords().entrySet().stream().limit(pivotId - 1)
                            .map(Map.Entry::getValue).collect(Collectors.toList()));
                }
            }
        }

        return toret;
    }

    private int findMainPivot(Sentence sentence) {
        int pivot = checkFirstGeneralCase(sentence);
        if (pivot == -1) pivot = checkSecondGeneralCase(sentence);
        if (pivot == -1) pivot = checkThirdGeneralCase(sentence);


        return pivot;
    }


    private int checkFirstGeneralCase(Sentence sentence) {
        for (int wordId : sentence.getWords().keySet()) {
            Word word = sentence.getWords().get(wordId);
            if (word.getDepRel().equals("appos") && word.getXPosTag().startsWith("V")) {
                return wordId;
            }
        }
        return -1;
    }

    private int checkSecondGeneralCase(Sentence sentence) {
        for (int wordId : sentence.getWords().keySet()) {
            Word word = sentence.getWords().get(wordId);
            if (word.getDepRel().equals("root") && word.getXPosTag().startsWith("V")) {
                int pivotPosition = checkIsGoingTo(sentence, word);
                return pivotPosition == -1 ? wordId : pivotPosition;
            }
        }
        return -1;
    }


    private int checkThirdGeneralCase(Sentence sentence) {
        for (int wordId : sentence.getWords().keySet()) {
            Word word = sentence.getWords().get(wordId);
            if (word.getDepRel().equals("root")) {
                //TODO revisar
                return sentence.getWords().values().stream().filter(i -> i.getDepRel().equals("cop")).findFirst().get().getId();
            }
        }
        return -1;
    }


    /**
     * @return id of the pivot or -1 if not this construct
     */
    private int checkIsGoingTo(Sentence sentence, Word word) {
        if (!word.getForm().equals("going"))
            return -1;
        int number = word.getId();
        if (sentence.getWords().get(word.getId() + 1).getXPosTag().equals("TO")
                && sentence.getWords().get(word.getId() + 2).getXPosTag().startsWith("V"))
            return number + 2;
        return -1;
    }


}
