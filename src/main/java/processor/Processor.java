package processor;

import model.xml.*;

public class Processor {


    public void process(ProcesedOutput out) {
        //TODO solo para hoy
        for (Sentence sentence : out.getSentences()) {
            for (Word word : sentence.getWords()) {
                //Primera casuistica descrita
                if (word.getDepRel().equals("appos") && word.getXPosTag().startsWith("V")) {
                    sentence.setPivot(word.getForm());
                    sentence.setTheme(sentence.getText().split(word.getForm())[0].trim());
                } else if (word.getDepRel().equals("root") && word.getXPosTag().startsWith("V")) {
                    //Segunda casuística
                    if (checkIsGoingTo(sentence)) {

                    } else {

                    }
                } else {
                    //Tercera casuística
                }
            }
        }
    }

    private boolean checkIsGoingTo(Sentence sentence) {


        return false;
    }


}
