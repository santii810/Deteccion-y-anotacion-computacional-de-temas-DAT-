package model.xml;

import lombok.*;

import javax.xml.bind.annotation.*;
import java.util.*;

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


    @XmlAttribute(name = "error")
    private String error;
    @XmlAttribute(name = "text")
    private String text;


    @XmlElementWrapper(name = "units")
    @XmlElement(name = "unit")
    private List<Unit> units = new LinkedList<>();

    @XmlElementWrapper(name = "words")
    @XmlElement(name = "word")
    private List<Word> words = new ArrayList<>();

}
