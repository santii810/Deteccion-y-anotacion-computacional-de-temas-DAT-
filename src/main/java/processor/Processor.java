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
            toret.add(processSentence(sentence));
        }
        return toret;
    }

    private SentenceXml processSentence(Sentence sentence) {
        SentenceXml xml = new SentenceXml();
        xml.setText(sentence.getText());
        int pivotId = findMainPivot(sentence);
        if (pivotId == -1) {
            xml.setState(State.KO);
        } else {
            xml.setState(State.OK);
            xml.setPivot(sentence.getWords().get(pivotId));
            if (pivotId == 1) xml.setTheme(Collections.singletonList(xml.getPivot()));
            xml.setTheme(sentence.getWords().entrySet().stream().limit(pivotId - 1)
                    .map(Map.Entry::getValue).collect(Collectors.toList()));
            xml.setTail(sentence.getWords().entrySet().stream().skip(pivotId)
                    .map(Map.Entry::getValue).collect(Collectors.toList()));

        }
        return xml;
    }

    private int findMainPivot(Sentence sentence) {
        //SpecialContructions
        int pivot = checkBeAboutTo(sentence);
        if (pivot == -1) pivot = checkLets(sentence);
        //Regular cases
        if (pivot == -1) pivot = checkFirstGeneralCase(sentence);
        if (pivot == -1) pivot = checkSecondGeneralCase(sentence);
        if (pivot == -1) pivot = checkThirdGeneralCase(sentence);


        return pivot;
    }

    private int checkLets(Sentence sentence) {
        int letPosition = sentence.getWords().values().stream().filter(i -> i.getLemma().equals("let")).findFirst().map(Word::getId).orElse(-1);
        if (letPosition != -1 && sentence.getWords().get(letPosition + 1).getLemma().equals("'s")
                && sentence.getWords().get(letPosition + 2).getDepRel().equals("xcomp") && sentence.getWords().get(letPosition + 2).getXPosTag().startsWith("V"))
            return letPosition + 2;
        return -1;
    }

    private int checkBeAboutTo(Sentence sentence) {
        int id = sentence.getWords().values().stream().filter(i -> i.getLemma().equals("be")).findFirst().map(Word::getId).orElse(-1);
        if (id != -1 && sentence.getWords().get(id + 1).getLemma().equals("about") && sentence.getWords().get(id + 2).getLemma().equals("to"))
            return id + 3;
        return -1;
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
                return sentence.getWords().values().stream().filter(i -> i.getDepRel().equals("cop")).findFirst().map(Word::getId).orElse(-1);
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
