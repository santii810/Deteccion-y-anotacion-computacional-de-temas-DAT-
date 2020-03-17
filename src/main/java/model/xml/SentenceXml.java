package model.xml;

import lombok.*;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
public class SentenceXml {

    @XmlAttribute(name = "ref")
    private int ref;

    @XmlAttribute(name = "state")
    private State state;

    @XmlElement(name = "result")
    private List<Result> result = new ArrayList<>();

    @XmlAttribute(name = "error")
    private String error;
    @XmlAttribute(name = "text")
    private String text;


}
