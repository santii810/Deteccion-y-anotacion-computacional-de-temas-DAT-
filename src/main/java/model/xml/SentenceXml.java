package model.xml;

import lombok.*;
import model.Word;

import javax.xml.bind.annotation.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
public class SentenceXml {

    @XmlAttribute(name = "text")
    private String text;

    @XmlAttribute(name = "state")
    private State state;

    @XmlElementWrapper(name = "theme")
    @XmlElement(name = "word")
    private List<Word> theme;

    @XmlElement(name = "pivot")
    private Word pivot;

    @XmlElementWrapper(name = "tail")
    @XmlElement(name = "word")
    private List<Word> tail;


}
