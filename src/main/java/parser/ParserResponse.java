package parser;


import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

@Getter
@Setter
public class ParserResponse {
    private int httpStatus;
    private JSONObject body;

    public ParserResponse(int httpStatus) {
        this.httpStatus = httpStatus;
        this.body = body;
    }
}
