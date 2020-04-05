package processor;

import lombok.extern.slf4j.Slf4j;
import mapper.ObjectsMapped;
import model.Sentence;
import model.xml.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
public class Processor {


    public void process(ObjectsMapped objectsMapped, ProcessedOutput output) {
        for (Sentence sentence : objectsMapped.getSentences()) {
            SentenceXml xml = detectUnitComponents(sentence);
            xml.setRef(output.getSentences().size() + 1);
            output.getSentences().add(xml);
        }
    }


    private SentenceXml detectUnitComponents(Sentence sentence) {
        SentenceXml xml = new SentenceXml();
        xml.setText(sentence.getText());
        xml.setWords(new ArrayList<>(sentence.getWords().values()));
        try {
            //Main units
            int mainPivotId = findMainPivot(sentence);
            if (mainPivotId == -1) {
                xml.setState(State.KO);
                xml.setError("Parsing error");
            } else {
                xml.setState(State.OK);
                if (mainPivotId == 1) {
                    xml.getUnits().add(new Unit("1", new Pivot(sentence.getWords().get(mainPivotId)),
                            new Theme(Collections.singletonList(sentence.getWords().get(mainPivotId))))
                    );
                } else {
                    xml.getUnits().add(new Unit("1",
                            new Pivot(sentence.getWords().get(mainPivotId)),
                            new Theme(sentence.getWords().entrySet().stream().limit(mainPivotId - 1)
                                    .map(Map.Entry::getValue).collect(Collectors.toList()))
                    ));
                }
            }


            //Secondary units
            xml.getUnits().addAll(findSecondaryPivots(sentence, mainPivotId));
            AtomicInteger ref = new AtomicInteger(1);
            xml.getUnits().stream().forEach(i-> i.setRef(String.valueOf(ref.getAndIncrement())));

            return xml;
        } catch (Exception e) {
            log.error("Error procesando sentencia: " + sentence.toString());
            log.error(e.getMessage());
            xml.setState(State.KO);
        }
        return xml;
    }

    private List<Word> findDependencyTree(List<Word> words, int id) {
        List<Word> toret = new ArrayList<>();
        List<Word> matches = words.stream().filter(i -> i.getHead() == id).collect(Collectors.toList());
        for (Word w : matches) {
            toret.addAll(findDependencyTree(words, w.getId()));
            toret.add(w);
        }
        return toret;
    }

    private List<Unit> findSecondaryPivots(Sentence sentence, int mainPivotId) {
        List<Unit> units = new ArrayList<>();
//CASO ESPECIAL
        sentence.getWords().values().stream()
                .filter(i -> i.getDepRel().equals("cop") && i.getId() != mainPivotId).forEach(pivot -> {
            Unit unit = new Unit();
            unit.setPivot(pivot);
            unit.setTheme(findDependencyTree(sentence.getWords().values().stream()
                            .filter(i -> i.getId() < pivot.getId()).collect(Collectors.toList()),
                    pivot.getHead()));
            units.add(unit);
        });

        sentence.getWords().values().stream()
                .filter(i -> i.getId() != mainPivotId && i.getXPosTag().startsWith("V")
                        && (i.getDepRel().startsWith("ccomp")
                        || i.getDepRel().startsWith("acl")
                        || i.getDepRel().startsWith("csubj")
                        || i.getDepRel().startsWith("advcl")
                        || i.getDepRel().equals("conj")
                        || i.getDepRel().startsWith("xcomp"))).forEach(pivot -> {
            findThemeOfPivot(sentence, units, pivot);
        });
        return units;
    }

    private void findThemeOfPivot(Sentence sentence, List<Unit> units, Word pivot) {
        Unit unit = new Unit();
        unit.setPivot(pivot);
        unit.setTheme(findDependencyTree(sentence.getWords().values().stream()
                        .filter(i -> i.getId() < pivot.getId()).collect(Collectors.toList()),
                pivot.getId()));
        units.add(unit);
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
