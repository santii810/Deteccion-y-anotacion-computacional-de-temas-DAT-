package processor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import model.Word;

@Setter
@Getter
@AllArgsConstructor
public class WordProcessor {
    private String line;

    public Word getProcesedWord() {
        String[] line = this.line.split("\\t");
        Word word = new Word();
        word.setText(line[1]);
        return word;
    }
}
