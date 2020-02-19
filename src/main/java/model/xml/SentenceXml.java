package model.xml;

import lombok.*;
import model.Word;

import javax.xml.bind.annotation.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class SentenceXml {

    @XmlElement(name = "pivot")
    private Word pivot;
    @XmlElementWrapper(name = "theme")
    @XmlElement(name = "word")
    private List<Word> theme;
}
