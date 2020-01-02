package parser;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.json.JSONObject;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "parserResponse")
public class ParserResponse {
    @XmlAttribute
    private int httpStatus;
    @XmlElement(name = "response")
    private String body;

    public ParserResponse(int httpStatus) {
        this.httpStatus = httpStatus;
        this.body = body;
    }
}
