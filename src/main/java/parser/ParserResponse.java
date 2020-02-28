package parser;


import lombok.*;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "parserResponse")
@ToString
public class ParserResponse {

    private int httpStatus;
    private String body;

    public ParserResponse(int httpStatus) {
        this.httpStatus = httpStatus;
    }
}
