package processor;

import model.Clause;
import model.ProcesedOutput;
import org.json.JSONArray;
import org.json.JSONObject;
import parser.ParserResponse;
import processor.model.ClauseProcessor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimpleProcessor implements Processor {
    @Override
    public ProcesedOutput process(List<ParserResponse> parserResponses) {
        ProcesedOutput procesedOutput = new ProcesedOutput();

        for (ParserResponse response : parserResponses) {
            JSONObject jsonObject = new JSONObject(response.getBody());
            //Añade un conjunto de agradecimientos al ProcesedOutput
            procesedOutput.getAcknowledgements().addAll(getAcknowledgements(jsonObject));
            //Añade la lista de Oraciones a procesar
            procesedOutput.setClauses(processToClauses((String) jsonObject.get("result"), procesedOutput));
        }
        return procesedOutput;
    }

    private List<Clause> processToClauses(String result, ProcesedOutput procesedOutput) {
        List<Clause> clauses = new ArrayList<>();
        for (String clause : result.split("\\n\\n")) {
            ClauseProcessor procesator = new ClauseProcessor(clause);
            clauses.add(procesator.getProcesedClause());
        }
        return clauses;
    }

    private Set<String> getAcknowledgements(JSONObject jsonObject) {
        JSONArray acknowledgements = jsonObject.getJSONArray("acknowledgements");
        Set<String> acks = new HashSet<>();
        for (Object acknowledgement : acknowledgements) {
            acks.add((String) acknowledgement);
        }
        return acks;
    }
}
