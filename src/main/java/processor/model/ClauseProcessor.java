package processor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import model.Clause;
import model.Word;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class ClauseProcessor {
    private String parsedClause;

    public Clause getProcesedClause() {
        Clause clause = new Clause();
        List<Word> words = new ArrayList<>();
        String[] lines = parsedClause.split("\\n");
        for (String line : lines) {
            if (line.charAt(0) == '#') {
                processClauseHeader(line, clause);
            } else {
                WordProcessor wordProcessor = new WordProcessor(line);
                words.add(wordProcessor.getProcesedWord());
            }
            System.out.println(line);
        }

        clause.setWords(words);
        return clause;
    }

    private void processClauseHeader(String line, Clause clause) {
        if (line.split(" ")[1].equals("text")) {
            clause.setText(line.substring(9));
        }
    }
}
